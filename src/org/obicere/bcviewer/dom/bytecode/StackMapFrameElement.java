package org.obicere.bcviewer.dom.bytecode;

import org.obicere.bcviewer.bytecode.StackMapFrame;
import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class StackMapFrameElement extends TextElement {

    private static final String APPEND_FRAME = "AppendFrame";

    private static final String CHOP_FRAME = "ChopFrame";

    private static final String FULL_FRAME = "FullFrame";

    private static final String SAME_FRAME = "SameFrame";

    private static final String SAME_FRAME_EXTENDED = "SameFrameExtended";

    private static final String SAME_LOCALS_1_STACK_ITEM_FRAME = "SameLocals";

    private static final String SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = "SameLocalsExtended";

    private static final String UNKNOWN = "Unknown";

    private static final int MAX_NAME_LENGTH = 18;

    public StackMapFrameElement(final StackMapFrame frame, final DocumentBuilder builder) {
        super(frame.getIdentifier());

        setText(getFrameName(frame.getFrameType()));
        setLeftPad(builder.getTabSize());
        setRightPad(builder.getTabbedPaddingSize(getText().length(), MAX_NAME_LENGTH));
        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_PLAIN));
    }

    private String getFrameName(final int frameType) {
        if (frameType <= 63) {
            return SAME_FRAME;
        } else if (frameType <= 127) {
            return SAME_LOCALS_1_STACK_ITEM_FRAME;
        } else if (frameType <= 246) {
            return UNKNOWN;
        } else if (frameType == 247) {
            return SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED;
        } else if (frameType <= 250) {
            return CHOP_FRAME;
        } else if (frameType == 251) {
            return SAME_FRAME_EXTENDED;
        } else if (frameType <= 254) {
            return APPEND_FRAME;
        } else if (frameType == 255) {
            return FULL_FRAME;
        } else {
            return UNKNOWN;
        }
    }
}
