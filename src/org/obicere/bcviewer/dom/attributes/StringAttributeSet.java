package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class StringAttributeSet extends SimpleAttributeSet {

    public StringAttributeSet(){
        addAttribute(StyleConstants.Foreground, new Color(0, 128, 52));
    }

}
