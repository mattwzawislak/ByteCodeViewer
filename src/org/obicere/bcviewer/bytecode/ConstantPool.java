package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantPool {

    private final Constant[] constants;

    public ConstantPool(final Constant[] constants){
        this.constants = constants;
    }

    public Constant get(final int index){
        return constants[index];
    }

    public String getAsString(final int index){
        final Constant constant = get(index);
        return String.valueOf(constant.get(this));
    }
}
