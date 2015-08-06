package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;

/**
 * @author Obicere
 */
public class CodeAttribute extends Attribute {

    private final int maxStack;

    private final int maxLocals;

    private final byte[] code;

    private final Instruction[] instructions;

    private final CodeException[] exceptions;

    private final Attribute[] attributes;

    public CodeAttribute(final int maxStack, final int maxLocals, final byte[] code, final Instruction[] instructions, final CodeException[] exceptions, final Attribute[] attributes) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.instructions = instructions;
        this.exceptions = exceptions;
        this.attributes = attributes;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public CodeException[] getExceptions() {
        return exceptions;
    }

    public byte[] getCode() {
        return code;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append("// Max stack: ");
        builder.append(maxStack);
        builder.append('\n');
        builder.append("// Max locals: ");
        builder.append(maxLocals);
        builder.append('\n');
        for (final Instruction instruction : instructions) {
            builder.append(instruction.toString(constantPool));
            builder.append('\n');
        }
        for (final CodeException exception : exceptions) {
            builder.append(exception.toString(constantPool));
            builder.append('\n');
        }
        if (attributes.length > 0) {
            builder.append('\n');
            builder.append("Attributes: ");
            builder.append('\n');
            for (final Attribute attribute : attributes) {
                builder.append(attribute.toString(constantPool));
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
