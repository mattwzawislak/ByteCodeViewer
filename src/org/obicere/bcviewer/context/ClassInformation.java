package org.obicere.bcviewer.context;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.InnerClass;
import org.obicere.bcviewer.bytecode.InnerClassesAttribute;
import org.obicere.bcviewer.reader.ClassFileReader;
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
public class ClassInformation {

    private final ClassFileReader classReader = new ClassFileReader();

    private final Map<String, ClassFile> classes = new HashMap<>();

    private ClassFile outerMostClass;

    public Collection<ClassFile> getLoadedClasses() {
        return classes.values();
    }

    public ClassFile getOuterMostClass() {
        return outerMostClass;
    }

    public ClassFile loadFile(final File file) throws IOException {
        try {
            return loadFileImplementation(false, file);
        } catch (final InnerOuterClassException e) {
            // really impossible to reach here
            return null;
        }
    }

    private ClassFile loadFileImplementation(final boolean needsInnerClass, final File file) throws IOException, InnerOuterClassException {
        final String name = file.getName();
        if (!name.endsWith(".class")) {
            throw new IOException("cannot load class from file that does not end with .class extension.");
        }

        // check to see if this class file is already loaded
        // if so, return the top of the class hierarchy for this load
        final ClassFile preloaded = classes.get(name);
        if (preloaded != null) {
            return outerMostClass;
        }

        // clear previously loaded classes
        classes.clear();

        // actualName is name without the .class extension
        final String actualName = removeClassExtension(name);
        final int innerIndex = actualName.lastIndexOf('$');

        // This will attempt to load the outer-most class, which is needed
        // to gain access to InnerClassAttributes, which show true
        // access flags
        if (innerIndex > 0) {
            final String possibleOuterName = actualName.substring(0, innerIndex);
            final File outerClass = new File(file.getParentFile(), possibleOuterName + ".class");
            if (outerClass.exists()) {
                try {
                    return loadFileImplementation(true, outerClass);
                } catch (final InnerOuterClassException e) {
                    // ignore, lets just drop through and load the previous
                }
            }
            // else we reached someone who doesn't follow convention
            // or a case where the inner class wasn't really an inner class
        }
        return loadOuterClass(needsInnerClass, file);
    }

    private ClassFile loadOuterClass(final boolean needsInnerClass, final File file) throws IOException, InnerOuterClassException {
        final ClassFile classFile = loadFully(needsInnerClass, file);
        outerMostClass = classFile;
        return classFile;
    }

    private ClassFile loadFully(final boolean needsInnerClass, final File file) throws IOException, InnerOuterClassException {
        // read all the bytes fully and generate a class file from them
        final byte[] bytes = IOUtils.readData(file);
        final IndexedDataInputStream input = new IndexedDataInputStream(bytes);
        final ClassFile classFile = classReader.read(input);

        final String name = classFile.getName();
        boolean hasInnerClass = false;
        for (final Attribute attribute : classFile.getAttributes()) {

            // check to see if this class has inner classes defined
            if (attribute instanceof InnerClassesAttribute) {

                hasInnerClass = true;
                // if so, get the prefix name for the qualified classes
                final String absolutePath = file.getAbsolutePath();

                // add the $ inner class separator automatically
                final String innerClassFilePrefix = removeClassExtension(absolutePath) + "$";

                // we'll need constant pool to get the inner class names
                final ConstantPool pool = classFile.getConstantPool();
                final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) attribute;
                for (final InnerClass innerClass : innerClassesAttribute.getInnerClasses()) {

                    // for inner classes, they have an inner class attribute
                    // which specifies itself as an inner class.
                    // this must be avoided by checking the names to see if
                    // they are the same name
                    final String innerClassInfo = pool.getAsString(innerClass.getInnerClassInfoIndex());
                    if (innerClassInfo.equals(name)) {
                        continue;
                    }

                    // get the name of the inner class
                    // if we have a class: example/Foo$Bar
                    // this would return 'Bar'
                    final String innerName = pool.getAsString(innerClass.getInnerNameIndex());
                    // append the name of the inner class to the file prefix
                    // and then add the appropriate extension
                    final String innerClassFileName = innerClassFilePrefix + innerName + ".class";

                    // load the class fully, also loading any inner classes
                    final ClassFile innerClassFile = loadFully(true, new File(innerClassFileName));

                    // this register is generally safe, since hasInnerClass
                    // will become true and the condition to break out of
                    // the system in the case an inner-outer error happens
                    // will therefore always be false
                    register(innerClassFile, pool.getAsString(innerClass.getInnerClassInfoIndex()));
                }
            }
        }
        if (needsInnerClass && !hasInnerClass) {
            throw new InnerOuterClassException();
        }
        // register self afterwards to be sure the load worked properly
        // the register of any inner classes is safe
        register(classFile, name);
        return classFile;
    }

    private void register(final ClassFile file, final String name) {
        classes.put(name, file);
    }

    private String removeClassExtension(final String name) {
        return name.substring(0, name.length() - 6);
    }

    private class InnerOuterClassException extends Exception {

    }
}
