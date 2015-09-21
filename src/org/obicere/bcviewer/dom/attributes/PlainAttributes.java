package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceAttributes;

/**
 */
public class PlainAttributes extends ResourceAttributes {
    public PlainAttributes() {
        super(FontResourcePool.FONT_BASELINE_PLAIN, ColorResourcePool.COLOR_PLAIN);
    }
}
