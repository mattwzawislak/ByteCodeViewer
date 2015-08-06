package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class Method extends BytecodeElement {

    private final int accessFlags;

    private final int nameIndex;

    private final int descriptorIndex;

    private final Attribute[] attributes;

    public Method(final int accessFlags, final int nameIndex, final int descriptorIndex, final Attribute[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append(constantPool.getAsString(nameIndex));
        builder.append("; ");
        builder.append(constantPool.getAsString(descriptorIndex));
        builder.append('\n');
        builder.append("access: ");
        builder.append(Integer.toHexString(accessFlags));
        builder.append('\n');
        for (final Attribute attribute : attributes) {
            builder.append(attribute.toString(constantPool));
            builder.append('\n');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
