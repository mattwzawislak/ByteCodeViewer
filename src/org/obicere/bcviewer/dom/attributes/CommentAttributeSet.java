package org.obicere.bcviewer.dom.attributes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 */
public class CommentAttributeSet extends SimpleAttributeSet {

    public CommentAttributeSet() {
        addAttribute(StyleConstants.Foreground, new Color(188, 188, 188));
        addAttribute(StyleConstants.Italic, true);
    }

}
