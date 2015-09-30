package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class TopVariableInfo extends VerificationTypeInfo {

    public TopVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder){
        builder.addKeyword("top");
    }
}
