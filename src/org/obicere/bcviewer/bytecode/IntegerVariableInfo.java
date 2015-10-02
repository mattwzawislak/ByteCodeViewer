package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class IntegerVariableInfo extends VerificationTypeInfo {

    public IntegerVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder) {
        builder.addKeyword("int");
    }

}
