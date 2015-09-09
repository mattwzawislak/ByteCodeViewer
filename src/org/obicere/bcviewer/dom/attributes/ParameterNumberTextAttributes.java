package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceTextAttributes;

/**
 */
public class ParameterNumberTextAttributes extends ResourceTextAttributes {

    public ParameterNumberTextAttributes() {
        super(FontResourcePool.FONT_BASELINE_PLAIN, ColorResourcePool.COLOR_PARAMETER_NUMBER);
    }
}
