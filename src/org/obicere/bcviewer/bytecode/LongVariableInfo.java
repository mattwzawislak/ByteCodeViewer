package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 */
public class LongVariableInfo extends VerificationTypeInfo {

    public LongVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addKeyword("long");
    }

}
