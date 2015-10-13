package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

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

    public abstract String toString(final ConstantPool constantPool);

    public abstract void modelValue(final DocumentBuilder builder);

    @Override
    public void model(final DocumentBuilder builder) {
        final String type = getName();
        builder.addKeyword(getName());
        builder.padTabbed(type.length(), MAX_NAME_LENGTH);
        builder.tab();
        modelValue(builder);
    }

}
