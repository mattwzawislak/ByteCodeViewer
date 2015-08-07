package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantPool {

    private final Constant[] constants;

    public ConstantPool(final Constant[] constants) {
        this.constants = constants;
    }

    public Constant get(final int index) {
        return constants[index];
    }

    public String getAsString(final int index) {
        final Constant constant = get(index);
        if (constant == null) {
            throw new NullPointerException();
        }
        return String.valueOf(constant.toString(this));
    }

    public String getAsCodeString(final int index) {
        final Constant constant = get(index);
        if (constant == null) {
            throw new NullPointerException();
        }
        final int tag = constant.getTag();
        switch (tag) {
            case ConstantReader.CONSTANT_DOUBLE:
            case ConstantReader.CONSTANT_FLOAT:
            case ConstantReader.CONSTANT_INTEGER:
            case ConstantReader.CONSTANT_LONG:
                return String.valueOf(constant.toString(this));
            default:
                return '"' + String.valueOf(constant.toString(this)) + '"';
        }
    }

    public Constant[] getConstants() {
        return constants;
    }
}
