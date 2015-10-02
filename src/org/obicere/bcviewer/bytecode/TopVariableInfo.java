package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class TopVariableInfo extends VerificationTypeInfo {

    public TopVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder){
        builder.addKeyword("top");
    }
}
