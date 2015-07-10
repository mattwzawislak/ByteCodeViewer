package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantMethodHandle extends Constant {

    private static final byte TAG = 15;

    private final byte referenceKind;

    private final int referenceIndex;

    public ConstantMethodHandle(final byte referenceKind, final int referenceIndex){
        super(TAG);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public byte getReferenceKind(){
        return referenceKind;
    }

    public int getReferenceIndex(){
        return referenceIndex;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(referenceIndex);
    }
}
