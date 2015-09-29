package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public abstract class StackMapFrame extends BytecodeElement {

    private static final int MAX_NAME_LENGTH = 23;

    private final int frameType;

    public StackMapFrame(final int frameType) {
        this.frameType = frameType;
    }

    public int getFrameType() {
        return frameType;
    }

    public abstract int getOffsetDelta();

    public abstract String getName();

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent){
        final String name = getName();
        builder.addPlain(parent, name);
        builder.padTabbed(parent, MAX_NAME_LENGTH, name.length());
        builder.addPlain(parent, "Offset: ");
        builder.add(parent, getOffsetDelta());
    }
}
