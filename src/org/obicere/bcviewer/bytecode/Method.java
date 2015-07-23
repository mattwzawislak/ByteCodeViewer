package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.instruction.InstructionReader;
import org.obicere.bcviewer.util.IndexedDataInputStream;

import java.io.IOException;

/**
 * @author Obicere
 */
public class Method {

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
            if (attribute instanceof CodeAttribute) {
                final InstructionReader reader = new InstructionReader();
                final IndexedDataInputStream input = new IndexedDataInputStream(((CodeAttribute) attribute).getCode());
                try {
                    while (input.available() > 0) {
                        builder.append(reader.read(input).getMnemonic());
                        builder.append('\n');
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                builder.append('\n');
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
