package org.obicere.bcviewer.dom.attributes;

import org.obicere.bcviewer.dom.ColorResourcePool;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.ResourceAttributes;

/**
 */
public class AnnotationAttributes extends ResourceAttributes {

    public AnnotationAttributes() {
        super(FontResourcePool.FONT_BASELINE_PLAIN, ColorResourcePool.COLOR_ANNOTATION);
    }
}
