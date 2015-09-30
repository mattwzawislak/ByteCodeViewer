package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class NullVariableInfo extends VerificationTypeInfo {

    public NullVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword("null");
    }

}
