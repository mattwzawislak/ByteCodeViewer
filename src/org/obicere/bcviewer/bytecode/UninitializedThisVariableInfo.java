package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class UninitializedThisVariableInfo extends VerificationTypeInfo {

    public UninitializedThisVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder) {
        builder.addKeyword("this");
        builder.add("->");
        builder.addKeyword("nullptr");
    }

}
