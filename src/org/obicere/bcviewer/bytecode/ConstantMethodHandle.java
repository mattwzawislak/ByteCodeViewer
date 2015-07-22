package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodHandle extends Constant {

    private final byte referenceKind;

    private final int referenceIndex;

    public ConstantMethodHandle(final byte referenceKind, final int referenceIndex){
        super(ConstantReader.CONSTANT_METHOD_HANDLE);
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
