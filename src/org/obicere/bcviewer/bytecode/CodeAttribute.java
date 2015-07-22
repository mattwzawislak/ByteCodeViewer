package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class CodeAttribute implements Attribute {

    private final int maxStack;

    private final int maxLocals;

    private final byte[] code;

    private final CodeException[] exceptions;

    private final Attribute[] attributes;

    public CodeAttribute(final int maxStack, final int maxLocals, final byte[] code, final CodeException[] exceptions, final Attribute[] attributes) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.exceptions = exceptions;
        this.attributes = attributes;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public Object[] getExceptions() {
        return exceptions;
    }

    public byte[] getCode() {
        return code;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}
