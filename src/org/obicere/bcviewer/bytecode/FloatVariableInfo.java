package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class FloatVariableInfo extends VerificationTypeInfo {

    public FloatVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword("float");
    }

}
