package org.obicere.bcviewer.dom;

/**
 */
public final class EmptyTextElement extends TextElement {

    public EmptyTextElement(final DocumentBuilder builder) {
        super("empty", "");
        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_PLAIN));
    }

    @Override
    public void setText(final String text) {

    }

    @Override
    public Caret getCaret(final int x, final int y) {
        // short-circuit the process, will be null anyway
        return null;
    }
}
