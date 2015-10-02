package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class NullVariableInfo extends VerificationTypeInfo {

    public NullVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder) {
        builder.addKeyword("null");
    }

}
