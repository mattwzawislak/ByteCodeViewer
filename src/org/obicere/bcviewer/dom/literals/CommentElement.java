package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class CommentElement extends TextElement {

    public CommentElement(final String name, final String comment, final DocumentBuilder builder) {
        super(name, "// " + comment);

        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_COMMENT));
    }
}
