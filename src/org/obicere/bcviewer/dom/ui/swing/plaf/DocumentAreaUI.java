package org.obicere.bcviewer.dom.ui.swing.plaf;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 */
public class DocumentAreaUI extends ComponentUI {

    @Override
    public void paint(final Graphics g, final JComponent component) {

    }

    @Override
    public Dimension getPreferredSize(final JComponent c) {
        if (c instanceof JDocumentArea) {
            final JDocumentArea area = (JDocumentArea) c;
            final Document document = area.getDocument();
            if (document == null) {
                return null;
            }
            return document.getPreferredSize();
        }
        return null;
    }

}
