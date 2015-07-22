package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodHandle extends Constant {

    private static final String[] HANDLES = new String[]{
            "unknown",
            "getfield",
            "getstatic",
            "putfield",
            "putstatic",
            "invokevirtual",
            "invokestatic",
            "invokespecial",
            "newinvokespecial",
            "invokeinterface"
    };

    private final int referenceKind;

    private final int referenceIndex;

    public ConstantMethodHandle(final int referenceKind, final int referenceIndex){
        super(ConstantReader.CONSTANT_METHOD_HANDLE);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public int getReferenceKind(){
        return referenceKind;
    }

    public int getReferenceIndex(){
        return referenceIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return HANDLES[referenceKind] + ";" + constantPool.getAsString(referenceIndex);
    }
}
