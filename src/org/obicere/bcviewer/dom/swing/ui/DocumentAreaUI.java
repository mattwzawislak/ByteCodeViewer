package org.obicere.bcviewer.dom.swing.ui;

import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.Style;
import org.obicere.bcviewer.dom.StyleConstraints;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.swing.JDocumentArea;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

/**
 */
public class DocumentAreaUI extends ComponentUI {

    public static DocumentAreaUI createUI(final JComponent component) {
        checkComponentType(component);
        return new DocumentAreaUI();
    }

    private static void checkComponentType(final JComponent component) {
        if (!(component instanceof JDocumentArea)) {
            throw new ClassCastException("expected a component of type JDocumentArea");
        }
    }

    @Override
    public void installUI(final JComponent component) {
        checkComponentType(component);
        final JDocumentArea area = (JDocumentArea) component;
        area.setFont(new QuickWidthFont("Courier new", Font.PLAIN, 12));
    }

    @Override
    public void uninstallUI(final JComponent component) {
        checkComponentType(component);
    }

    @Override
    public void paint(final Graphics g, final JComponent component) {
        checkComponentType(component);
        final JDocumentArea area = (JDocumentArea) component;
        final QuickWidthFont font = (QuickWidthFont) area.getFont();
        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();

        final Rectangle visible = area.getVisibleRect();
        final Insets insets = area.getInsets();

        final int startLine = (visible.y + insets.top) / fontHeight;
        final int unboundedEndLine = (visible.height + visible.y + insets.top + fontHeight) / fontHeight;

        final int endLine = Math.min(unboundedEndLine, area.getLineCount());

        int y = fontHeight + startLine * fontHeight;

        final List<Line> lines = area.getLines(startLine, endLine);

        for (final Line line : lines) {
            if (line == null) {
                y += fontHeight;
                continue;
            }

            final char[] chars = line.getChars();
            if (chars.length == 0) {
                // handle empty line case
                y += fontHeight;
                continue;
            }

            final StyleConstraints constraints = line.getConstraints();

            final Iterator<Integer> bounds = constraints.boundsIterator();
            final Iterator<Style> styles = constraints.styleIterator();

            int start = bounds.next();
            int x = -insets.left;
            while (bounds.hasNext()) {
                final Style style = styles.next();
                final int length = bounds.next() - start;

                if (style != null) {
                    g.setColor(style.getColor());
                    g.setFont(style.getFont());
                }

                g.drawChars(chars, start, length, x, y);

                x += fontWidth * length;

                start += length;
            }
            y += fontHeight;
        }
    }

    @Override
    public Dimension getPreferredSize(final JComponent component) {
        checkComponentType(component);
        final JDocumentArea area = (JDocumentArea) component;
        final QuickWidthFont font = (QuickWidthFont) area.getFont();

        final Dimension dimension = getDocumentSize(area, font.getFixedWidth(), font.getFixedHeight());
        final Insets insets = area.getInsets();
        dimension.width -= insets.left + insets.right;
        dimension.height -= insets.top + insets.bottom;
        return dimension;
    }

    private Dimension getDocumentSize(final JDocumentArea area, final int fontWidth, final int fontHeight) {
        return new Dimension(area.getMaxLineLength() * fontWidth, area.getLineCount() * fontHeight);
    }
}
