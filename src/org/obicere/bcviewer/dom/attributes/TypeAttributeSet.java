package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class TypeAttributeSet extends SimpleAttributeSet {

    public TypeAttributeSet() {
        addAttribute(StyleConstants.Foreground, new Color(86, 151, 250));
    }

}
