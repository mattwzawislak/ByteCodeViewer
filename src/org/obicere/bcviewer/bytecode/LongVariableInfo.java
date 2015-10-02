package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class LongVariableInfo extends VerificationTypeInfo {

    public LongVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder) {
        builder.addKeyword("long");
    }

}
