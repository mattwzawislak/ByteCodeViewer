package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceTextAttributes;

/**
 */
public class ConstantTextAttributes extends ResourceTextAttributes {

    public ConstantTextAttributes() {
        super(FontResourcePool.FONT_BASELINE_PLAIN, ColorResourcePool.COLOR_CONSTANT);
    }
}
