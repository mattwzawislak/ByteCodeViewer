package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 */
public class UninitializedVariableInfo extends VerificationTypeInfo {

    private final int offset;

    public UninitializedVariableInfo(final int tag, final int offset) {
        super(tag);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addKeyword(parent, "nullptr");
    }
}
