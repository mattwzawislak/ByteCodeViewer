package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 */
public class UninitializedThisVariableInfo extends VerificationTypeInfo {

    public UninitializedThisVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addKeyword(parent, "this");
        builder.addPlain(parent, "->");
        builder.addKeyword(parent, "nullptr");
    }

}
