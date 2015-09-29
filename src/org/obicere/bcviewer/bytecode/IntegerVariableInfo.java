package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 */
public class IntegerVariableInfo extends VerificationTypeInfo {

    public IntegerVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addKeyword(parent, "int");
    }

}
