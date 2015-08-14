package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class InnerClassesAttribute extends Attribute {

    private final InnerClass[] classes;

    public InnerClassesAttribute(final InnerClass[] classes) {

        if (classes == null) {
            throw new NullPointerException("inner classes not defined.");
        }

        this.classes = classes;
    }

    public InnerClass[] getInnerClasses() {
        return classes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append("InnerClassesAttribute: ");
        for (final InnerClass innerClass : classes) {
            builder.append('\n');
            builder.append(innerClass.toString(constantPool));
        }
        return builder.toString();
    }
}
