package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class Constant {

    private final byte tag;

    public Constant(final byte tag){
        this.tag = tag;
    }

    public final byte getTag(){
        return tag;
    }

    public abstract Object get(final ConstantPool constantPool);
}
