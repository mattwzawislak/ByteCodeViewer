package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.StackMapFrameElement;

/**
 * @author Obicere
 */
public class AppendFrame extends StackMapFrame {

    private final int                    offset;
    private final VerificationTypeInfo[] locals;

    public AppendFrame(final int frameType, final int offset, final VerificationTypeInfo[] locals) {
        super(frameType);

        if (locals.length != frameType - 251) {
            throw new IllegalArgumentException("invalid locals count for frame: " + locals.length);
        }

        this.offset = offset;
        this.locals = locals;
    }

    public int getOffset() {
        return offset;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }

    @Override
    public int getOffsetDelta() {
        return offset;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent){
        parent.add(new StackMapFrameElement(this, builder));
    }
}
