package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceAttributes;

/**
 */
public class StringAttributes extends ResourceAttributes {

    public StringAttributes() {
        super(FontResourcePool.FONT_BASELINE_PLAIN, ColorResourcePool.COLOR_STRING);
    }
}
