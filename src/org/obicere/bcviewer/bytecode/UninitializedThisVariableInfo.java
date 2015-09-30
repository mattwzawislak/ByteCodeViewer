package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public class UninitializedThisVariableInfo extends VerificationTypeInfo {

    public UninitializedThisVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword("this");
        builder.add("->");
        builder.addKeyword("nullptr");
    }

}
