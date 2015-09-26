package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;

/**
 */
public class TopVariableInfo extends VerificationTypeInfo {

    public TopVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent){
        // do nothing?
        // top has no relevant information
    }
}
