package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class FloatVariableInfo extends VerificationTypeInfo {

    public FloatVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder) {
        builder.addKeyword("float");
    }

}
