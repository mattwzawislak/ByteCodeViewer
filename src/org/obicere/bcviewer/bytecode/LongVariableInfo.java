package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class LongVariableInfo extends VerificationTypeInfo {

    public LongVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword("long");
    }

}
