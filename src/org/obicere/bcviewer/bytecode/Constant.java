package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public abstract class Constant extends BytecodeElement {

    private static final int MAX_NAME_LENGTH = 11;

    private final int tag;

    public Constant(final int tag) {
        this.tag = tag;
    }

    public final int getTag() {
        return tag;
    }

    @Override
    public String getIdentifier() {
        return "constant" + getStart();
    }

    public abstract String getName();

    public abstract void modelValue(final BytecodeDocumentBuilder builder, final Element parent);

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent){
        final String type = getName();
        builder.addKeyword(parent, getName());
        builder.padTabbed(parent, MAX_NAME_LENGTH, type.length());
        builder.tab(parent);
        modelValue(builder, parent);
    }

}
