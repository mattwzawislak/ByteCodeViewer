package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceAttributes;

/**
 */
public class CommentAttributes extends ResourceAttributes {

    public CommentAttributes() {
        super(FontResourcePool.FONT_BASELINE_ITALIC, ColorResourcePool.COLOR_COMMENT);
    }
}
