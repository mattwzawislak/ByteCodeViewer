package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class IntegerVariableInfo extends VerificationTypeInfo {

    public IntegerVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword("int");
    }

}
