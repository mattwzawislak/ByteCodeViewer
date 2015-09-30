package org.obicere.bcviewer.dom.swing.ui;

import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.Style;
import org.obicere.bcviewer.dom.StyleConstraints;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.swing.Caret;
import org.obicere.bcviewer.dom.swing.JDocumentArea;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

/**
 */
public class DocumentAreaUI extends ComponentUI {

    private static final int LEFT_MARGIN_WIDTH = 10;

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

        final DocumentMouseListener mouseListener = new DocumentMouseListener(area);
        area.addMouseListener(mouseListener);
    }

    @Override
    public void uninstallUI(final JComponent component) {
        checkComponentType(component);
    }

    @Override
    public void paint(final Graphics g, final JComponent component) {
        checkComponentType(component);

        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        final JDocumentArea area = (JDocumentArea) component;
        final QuickWidthFont font = (QuickWidthFont) area.getFont();
        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();

        final Rectangle visible = area.getVisibleRect();

        final int visibleTop = visible.y;
        // add fontHeight for integer division and ensuring last line
        // gets drawn - even if just partially
        final int visibleMiddle = visibleTop + visible.height + fontHeight;

        final int startLine = visibleTop / fontHeight;
        final int unboundedEndLine = visibleMiddle / fontHeight;

        final int endLine = Math.min(unboundedEndLine, area.getLineCount());

        drawCollapsibleMarkers(g, area, startLine, endLine, fontHeight);

        drawCaret(g, area.getCaret(), startLine, endLine, fontWidth, fontHeight, area.isThinCarets());
        drawCaret(g, area.getDropCaret(), startLine, endLine, fontWidth, fontHeight, area.isThinCarets());

        final List<Line> lines = area.getLines(startLine, endLine);

        drawLines(g, lines, fontWidth, fontHeight, startLine);

    }

    private void drawLines(final Graphics g, final List<Line> lines, final int fontWidth, final int fontHeight, int startLine){
        int y = fontHeight + startLine * fontHeight;
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
            int x = LEFT_MARGIN_WIDTH;
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

    private void drawCaret(final Graphics g, final Caret caret, final int startLine, final int endLine, final int fontWidth, final int fontHeight, final boolean thin) {
        if (!caret.isPlaced()) {
            return;
        }
        final int row = caret.getRow();
        final int column = caret.getColumn();
        if (startLine <= row && row <= endLine) {
            final int x = column * fontWidth + LEFT_MARGIN_WIDTH;
            final int y = row * fontHeight;

            // lets just use an approximation of + 2 for the descent
            // or whichever font metric is used to offset these damn things
            g.fillRect(x, y + 2, thin ? 1 : 2, fontHeight);
        }
    }

    private void drawCollapsibleMarkers(final Graphics g, final JDocumentArea area, final int startLine, final int endLine, final int fontHeight) {

        for (final Block block : area.getBlocks()) {
            if (block.isCollapsible()) {
                final boolean blockVisible = block.isVisible();
                final int start = block.getLineStart();
                final int end = block.getLineEnd();
                final int yStart = start * fontHeight;
                final int yEnd = end * fontHeight;

                final int visibleTop = startLine * fontHeight;
                final int visibleMiddle = endLine * fontHeight;
                if ((startLine <= start && start <= endLine) || (startLine <= end && end <= endLine)) {
                    // find the appropriate line and subtract the invisible
                    // top of the document
                    // draw the top box
                    if (visibleTop <= yStart && yStart <= visibleMiddle) {
                        drawPlusMinusBox(g, yStart + fontHeight, !blockVisible);
                    }
                    if (blockVisible) {
                        if (visibleTop <= yEnd && yEnd <= visibleMiddle) {
                            drawPlusMinusBox(g, yEnd, false);
                        }
                    }
                }
                if (blockVisible) {

                    final int lineStart = yStart + fontHeight;
                    final int clippedLineStart = Math.max(lineStart, visibleTop);
                    final int lineEnd = yEnd - 8; // - box height
                    final int clippedLineEnd = Math.min(lineEnd, visibleMiddle);

                    g.drawLine(5, clippedLineStart, 5, clippedLineEnd);
                }
            }
        }
    }

    private void drawPlusMinusBox(final Graphics g, final int baseline, final boolean plus) {
        g.drawRect(1, baseline - 8, 8, 8);
        g.drawLine(3, baseline - 4, 7, baseline - 4);
        if (plus) {
            g.drawLine(5, baseline - 6, 5, baseline - 2);
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
        return new Dimension(area.getMaxLineLength() * fontWidth, area.getLineCount() * fontHeight + fontHeight);
    }

    private class DocumentMouseListener extends MouseAdapter {

        private final JDocumentArea area;

        private final int fontWidth;
        private final int fontHeight;

        public DocumentMouseListener(final JDocumentArea area) {
            this.area = area;
            final QuickWidthFont font = (QuickWidthFont) area.getFont();
            this.fontWidth = font.getFixedWidth();
            this.fontHeight = font.getFixedHeight();
        }

        @Override
        public void mousePressed(final MouseEvent event) {
            final int x = event.getX();
            final int y = event.getY();
            if (x < LEFT_MARGIN_WIDTH) {
                // click in the margin. Only collapse markers receive
                // clicks in this area
                handleCloseBlock(y);
            } else {
                // otherwise, we're directly clicking the text area
                // we then offset the margin, to make calculation
                // clearer later on
                handleCursorPlacement(x - LEFT_MARGIN_WIDTH, y);
            }
        }

        private void handleCursorPlacement(final int x, final int y) {
            area.getCaret().setLocation(y / fontHeight, x / fontWidth);
            area.repaint();
        }

        private void handleCloseBlock(final int y) {
            for (final Block block : area.getBlocks()) {

                // ensure the block is collapsible before any calculation
                // on its marker's locations
                if (block.isCollapsible()) {
                    final int start = block.getLineStart();
                    final int end = block.getLineEnd();
                    final int yStart = start * fontHeight;
                    final int yEnd = end * fontHeight - fontHeight;

                    // handle the top marker - visible always
                    if (yStart <= y && y <= yStart + fontHeight) {
                        markerPressed(block, !block.isVisible());
                        return;
                    }
                    // handle lower marker - only visible when block is
                    if (block.isVisible() && yEnd <= y && y <= yEnd + fontHeight) {
                        markerPressed(block, false);
                        return;
                    }
                }
            }
        }

        private void markerPressed(final Block block, final boolean visible) {
            area.setBlockVisible(block, visible);
            area.revalidate();
            area.repaint();
        }
    }
}
