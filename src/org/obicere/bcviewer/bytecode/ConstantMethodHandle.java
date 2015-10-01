package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodHandle extends Constant {

    private static final String NAME = "MethodHandle";

    private static final String[] HANDLES = new String[]{
            null,
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

    public ConstantMethodHandle(final int referenceKind, final int referenceIndex) {
        super(ConstantReader.CONSTANT_METHOD_HANDLE);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public int getReferenceKind() {
        return referenceKind;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return HANDLES[referenceKind] + "=" + constantPool.getAsString(referenceIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder) {
        builder.addKeyword(HANDLES[referenceKind]);
        builder.tab();
        builder.getConstantPool().get(referenceIndex).modelValue(builder);
    }
}
