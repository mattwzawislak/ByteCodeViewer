package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class AnnotationAttributeSet extends SimpleAttributeSet {

    public AnnotationAttributeSet(){
        addAttribute(StyleConstants.Foreground, new Color(167, 185, 44));
    }

}
