package org.obicere.bcviewer.dom.ui.swing.plaf;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.View;
import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

/**
 */
public class DocumentAreaUI extends ComponentUI {

    @Override
    public void paint(final Graphics g, final JComponent component) {
        if (component instanceof JDocumentArea) {
            final JDocumentArea area = (JDocumentArea) component;
            final Insets insets = area.getInsets();
            final Document document = area.getDocument();
            if (document != null) {
                final View<?> view = document.getView();
                view.layout(insets.left, insets.top);
                view.paint(g);

            }
        } else {
            throw new IllegalArgumentException("type of component must be " + getClass().getName());
        }
    }

    @Override
    public Dimension getPreferredSize(final JComponent component) {
        if (component instanceof JDocumentArea) {
            final JDocumentArea area = (JDocumentArea) component;
            final Insets insets = area.getInsets();
            final Document document = area.getDocument();
            if (document == null) {
                return null;
            }
            final Dimension dimensions = document.getPreferredSize();
            dimensions.width -= insets.left + insets.right;
            dimensions.height -= insets.top + insets.bottom;
            return dimensions;
        }
        return null;
    }

    public static ComponentUI createUI(final JComponent component) {
        return new DocumentAreaUI();
    }

    @Override
    public int getBaseline(final JComponent component, final int width, final int height) {
        super.getBaseline(component, width, height);

        final Insets insets = component.getInsets();
        final Font font = component.getFont();
        return insets.top + component.getFontMetrics(font).getAscent();
    }

    @Override
    public Component.BaselineResizeBehavior getBaselineResizeBehavior(final JComponent component) {
        if (component == null) {
            throw new NullPointerException("Component must be non-null.");
        }
        return Component.BaselineResizeBehavior.CONSTANT_ASCENT;

    }

}
