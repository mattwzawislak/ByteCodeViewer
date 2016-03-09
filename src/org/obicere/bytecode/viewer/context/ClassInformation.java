package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.objects.Attribute;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.util.IndexedDataInputStream;
import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final Map<String, ClassFile> classes = new HashMap<>();

    private Path fileSource;

    private ClassFile rootClass;

    private final Domain domain;

    public ClassInformation(final Domain domain) {
        this.domain = domain;
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

        classes.clear();
    }

    public ClassFile load(final Path fileSource) throws IOException {
        this.fileSource = fileSource;
        final byte[] classBytes = Files.readAllBytes(fileSource);
        this.rootClass = domain.getClassReader().read(new IndexedDataInputStream(classBytes));
        classes.put(rootClass.getName(), rootClass);

        loadInnerClasses(rootClass);
        return rootClass;
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
                    if (innerClassFile == null) {
                        Logger.getGlobal().log(Level.WARNING, "Could not load inner class: " + simpleName);
                    } else {
                        classes.put(name, innerClassFile);

                        loadInnerClasses(innerClassFile);
                    }
                }
            }
        }
    }

    private ClassFile loadFrom(final Path file) throws IOException {
        if (!Files.exists(file)) {
            return null;
        }
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
