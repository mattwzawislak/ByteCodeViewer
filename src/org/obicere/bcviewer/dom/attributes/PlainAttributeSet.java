package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class PlainAttributeSet extends SimpleAttributeSet {

    public PlainAttributeSet(){
       addAttribute(StyleConstants.Foreground, new Color(16, 16, 16));
    }

}
