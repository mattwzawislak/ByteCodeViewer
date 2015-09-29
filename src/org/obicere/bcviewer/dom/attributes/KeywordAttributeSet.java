package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class KeywordAttributeSet extends SimpleAttributeSet {

    public KeywordAttributeSet(){
        addAttribute(StyleConstants.Foreground, new Color(227, 80, 0));
    }

}
