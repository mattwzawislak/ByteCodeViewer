package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class Constant {

    private final int tag;

    public Constant(final int tag){
        this.tag = tag;
    }

    public final int getTag(){
        return tag;
    }

    public abstract String toString(final ConstantPool constantPool);
}
