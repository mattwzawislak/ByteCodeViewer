package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.objects.Attribute;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.util.IndexedDataInputStream;
import org.obicere.bytecode.viewer.io.Source;
import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.IOException;
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

    private Source fileSource;

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

    public Source getFileSource() {
        return fileSource;
    }

    public void clear() {
        rootClass = null;

        classes.clear();
    }

    public ClassFile load(final Source fileSource, final byte[] classBytes) throws IOException {
        final IndexedDataInputStream stream = new IndexedDataInputStream(classBytes);

        this.fileSource = fileSource;
        this.rootClass = domain.getClassReader().read(stream);
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

                    final Source innerFile = fileSource.getSibling(simpleName + ".class");
                    if (innerFile == null) {
                        Logger.getGlobal().log(Level.WARNING, "Could not find inner class file: " + simpleName + " for class: " + file.getName());
                    } else {
                        final ClassFile innerClassFile = loadFrom(innerFile);
                        if (innerClassFile == null) {
                            Logger.getGlobal().log(Level.WARNING, "Could not load inner class: " + simpleName + " for class: " + file.getName());
                        } else {
                            classes.put(name, innerClassFile);

                            loadInnerClasses(innerClassFile);
                        }
                    }
                }
            }
        }
    }

    private ClassFile loadFrom(final Source file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        final byte[] bytes = file.read();
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
