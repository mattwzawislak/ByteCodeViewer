package org.obicere.bcviewer.context;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.InnerClass;
import org.obicere.bcviewer.bytecode.InnerClassesAttribute;
import org.obicere.bcviewer.concurrent.ClassCallback;
import org.obicere.bcviewer.util.FileUtils;
import org.obicere.bcviewer.util.IndexedDataInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final Map<String, ClassFile> classes = new HashMap<>();

    private Path fileSource;

    private ClassFile rootClass;

    private byte[] classBytes;

    private final Domain domain;

    public ClassInformation(final Domain domain) {
        this.domain = domain;
    }

    public byte[] getClassBytes() {
        return classBytes;
    }

    public Collection<ClassFile> getLoadedClasses() {
        return classes.values();
    }

    public ClassFile getRootClass() {
        return rootClass;
    }

    public Path getFileSource() {
        return fileSource;
    }

    public void clear() {
        rootClass = null;
        classBytes = null;

        classes.clear();
    }

    public ClassFile load(final ClassCallback callback, final Path fileSource) {
        try {
            this.fileSource = fileSource;
            this.classBytes = Files.readAllBytes(fileSource);
            this.rootClass = domain.getClassReader().read(new IndexedDataInputStream(classBytes));
            classes.put(rootClass.getName(), rootClass);

            loadInnerClasses(rootClass);
            try {
                return rootClass;
            } finally {
                callback.notifyInformationComplete(this);
            }
        } catch (final Throwable e) {
            callback.notifyThrowable(e);
            return null;
        }
    }

    private void loadInnerClasses(final ClassFile file) throws IOException {
        final ConstantPool constantPool = file.getConstantPool();
        for (final Attribute attribute : file.getAttributes()) {
            if (attribute instanceof InnerClassesAttribute) {
                final InnerClass[] innerClasses = ((InnerClassesAttribute) attribute).getInnerClasses();

                for (final InnerClass innerClass : innerClasses) {
                    final String name = constantPool.getAsString(innerClass.getInnerClassInfoIndex());
                    final String outer = constantPool.getAsString(innerClass.getOuterClassInfoIndex());

                    // make sure we aren't processing self (every inner
                    // class contains itself in the list of its inner
                    // classes and ensure the class isn't already added
                    if (name.equals(file.getName()) || classes.get(name) != null) {
                        continue;
                    }
                    // method enclosed classes have outer be a null entry
                    // otherwise, this class should be within the file
                    // and ensure it isn't a Lookup
                    if (!outer.equals("<null entry>") && !file.getName().equals(outer)) {
                        continue;
                    }

                    final String simpleName = FileUtils.getFileName(name);

                    final Path innerFile = fileSource.resolveSibling(simpleName + ".class");
                    final ClassFile innerClassFile = loadFrom(innerFile);
                    classes.put(name, innerClassFile);

                    loadInnerClasses(innerClassFile);
                }
            }
        }
    }

    private ClassFile loadFrom(final Path file) throws IOException {
        final byte[] bytes = Files.readAllBytes(file);
        return domain.getClassReader().read(new IndexedDataInputStream(bytes));
    }

    public ClassFile getClass(final String name) {
        return classes.get(name);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
