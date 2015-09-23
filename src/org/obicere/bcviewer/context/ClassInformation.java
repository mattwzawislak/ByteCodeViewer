package org.obicere.bcviewer.context;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.InnerClass;
import org.obicere.bcviewer.bytecode.InnerClassesAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.utility.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final Map<String, ClassFile> classes = new HashMap<>();

    private ClassFile rootClass;

    private File parentFile;

    private Domain domain;

    public ClassInformation(final Domain domain) {
        this.domain = domain;
    }

    public Collection<ClassFile> getLoadedClasses() {
        return classes.values();
    }

    public ClassFile getRootClass() {
        return rootClass;
    }

    public ClassFile load(final File file) throws IOException {
        this.parentFile = file.getParentFile();

        this.rootClass = loadFrom(file);
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

                    if(name.equals(file.getName())){
                        continue;
                    }
                    // method enclosed classes have outer be a null entry
                    if (!outer.equals("<null entry>") && !file.getName().equals(outer) || "java/lang/invoke/MethodHandles$Lookup".equals(name)) {
                        continue;
                    }
                    final String simpleName = name.substring(name.lastIndexOf('/') + 1);
                    final File innerFile = new File(parentFile, simpleName + ".class");
                    final ClassFile innerClassFile = loadFrom(innerFile);
                    classes.put(name, innerClassFile);

                    loadInnerClasses(innerClassFile);
                }
            }
        }
    }

    private ClassFile loadFrom(final File file) throws IOException {
        final byte[] bytes = IOUtils.readData(file);
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
