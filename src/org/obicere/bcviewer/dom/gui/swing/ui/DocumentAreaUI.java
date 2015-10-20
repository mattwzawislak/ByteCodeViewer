package org.obicere.bcviewer.dom.gui.swing.ui;

import org.obicere.bcviewer.Boot;
import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.gui.swing.Caret;
import org.obicere.bcviewer.dom.gui.swing.JDocumentArea;
import org.obicere.bcviewer.dom.style.Style;
import org.obicere.bcviewer.dom.style.StyleConstraints;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.settings.Settings;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

/**
 */
public class DocumentAreaUI extends ComponentUI {

    private static final int LEFT_MARGIN_WIDTH = 10;

    private final JDocumentArea area;

    public DocumentAreaUI(final JDocumentArea area) {
        this.area = area;
    }

    public static DocumentAreaUI createUI(final JComponent component) {
        checkComponentType(component);
        return new DocumentAreaUI((JDocumentArea) component);
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
        // use the textfield background color for this background
        area.setBackground(UIManager.getColor("TextField.background"));
        area.setFocusable(true);

        final Font font = Boot.getGlobalDomain().getSettingsController().getSettings().getFont("editor.font");
        final QuickWidthFont quickWidthFont = new QuickWidthFont(font.getName(), Font.PLAIN, font.getSize());
        area.setFont(quickWidthFont);

        installMouse(area);
        installKeys(area);
    }

    private void installMouse(final JDocumentArea area) {
        final DocumentMouseListener mouseListener = new DocumentMouseListener();
        area.addMouseListener(mouseListener);
        area.addMouseMotionListener(mouseListener);
    }

    private void uninstallMouse(final JDocumentArea area) {
        final DocumentMouseListener[] dmListener = area.getListeners(DocumentMouseListener.class);
        for (final DocumentMouseListener listener : dmListener) {
            area.removeMouseListener(listener);
            area.removeMouseMotionListener(listener);
        }
    }

    private void installKeys(final JDocumentArea area) {
        final InputMap inputs = area.getInputMap();
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), CaretRightAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), CaretLeftAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), CaretDownAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), CaretUpAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK), SelectAllAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), CopyAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), PageUpAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), PageDownAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), ReloadAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.CTRL_DOWN_MASK), HardReloadAction.NAME);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CloseAction.NAME);

        final ActionMap actions = area.getActionMap();
        actions.put(CaretRightAction.NAME, new CaretRightAction());
        actions.put(CaretLeftAction.NAME, new CaretLeftAction());
        actions.put(CaretDownAction.NAME, new CaretDownAction());
        actions.put(CaretUpAction.NAME, new CaretUpAction());
        actions.put(SelectAllAction.NAME, new SelectAllAction());
        actions.put(CopyAction.NAME, new CopyAction());
        actions.put(PageUpAction.NAME, new PageUpAction());
        actions.put(PageDownAction.NAME, new PageDownAction());
        actions.put(ReloadAction.NAME, new ReloadAction());
        actions.put(HardReloadAction.NAME, new HardReloadAction());
        actions.put(CloseAction.NAME, new CloseAction());
    }

    @Override
    public void uninstallUI(final JComponent component) {
        checkComponentType(component);
        final JDocumentArea area = (JDocumentArea) component;

        uninstallMouse(area);
    }

    @Override
    public void paint(final Graphics g, final JComponent component) {
        checkComponentType(component);
        final JDocumentArea area = (JDocumentArea) component;

        final Graphics2D g2 = (Graphics2D) g;

        final Settings settings = area.getDomain().getSettingsController().getSettings();

        if (settings.getBoolean("editor.textAA")) {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        if (settings.getBoolean("editor.colorAA")) {
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        }

        final QuickWidthFont font = (QuickWidthFont) area.getFont();
        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();

        final Rectangle visible = area.getVisibleRect();

        g.setColor(area.getBackground());
        g.fillRect(visible.x, visible.y, visible.width, visible.height);
        g.setColor(area.getForeground());

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
        drawSelection(g, startLine, endLine, fontWidth, fontHeight);

        final List<Line> lines = area.getLines(startLine, endLine);

        drawLines(g, lines, fontWidth, fontHeight, startLine);

    }

    private void drawLines(final Graphics g, final List<Line> lines, final int fontWidth, final int fontHeight, int startLine) {
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
        final FontMetrics metrics = g.getFontMetrics();
        final int row = caret.getRow();
        final int column = caret.getColumn();
        if (startLine <= row && row <= endLine) {
            final int x = column * fontWidth + LEFT_MARGIN_WIDTH;
            final int y = row * fontHeight;

            g.fillRect(x, y + metrics.getDescent(), thin ? 1 : 2, fontHeight);
        }
    }

    private void drawSelection(final Graphics g, final int startLine, final int endLine, final int fontWidth, final int fontHeight) {
        final Caret caret = area.getCaret();
        final Caret dropCaret = area.getDropCaret();

        if (!caret.isPlaced() || !dropCaret.isPlaced()) {
            return;
        }

        final int caretRow = caret.getRow();
        final int dropCaretRow = dropCaret.getRow();

        final int startRow = Math.min(caretRow, dropCaretRow);
        final int endRow = Math.max(caretRow, dropCaretRow);

        if (startRow > endLine || endRow < startLine) {
            // the highlight is not visible
            return;
        }

        final int caretColumn = caret.getColumn();
        final int dropCaretColumn = dropCaret.getColumn();

        g.setColor(area.getHighlightColor());
        if (caretRow == dropCaretRow) {
            final int start = Math.min(caretColumn, dropCaretColumn);
            final int end = Math.max(caretColumn, dropCaretColumn);
            if (start == end) {
                return;
            }
            drawHighlightOnLine(g, start, end, caretRow, fontWidth, fontHeight);
        } else {
            final int startColumn;
            final int endColumn;
            if (caretRow < dropCaretRow) {
                startColumn = caretColumn;
                endColumn = dropCaretColumn;
            } else {
                startColumn = dropCaretColumn;
                endColumn = caretColumn;
            }

            if (startRow >= startLine) {
                drawHighlightOnLine(g, startColumn, area.getLine(startRow).length(), startRow, fontWidth, fontHeight);
            }

            final int clippedStartLine = Math.max(startLine, startRow + 1);
            final int clippedEndLine = Math.min(endRow, endLine);
            for (int i = clippedStartLine; i < clippedEndLine; i++) {
                drawHighlightOnLine(g, 0, area.getLine(i).length(), i, fontWidth, fontHeight);
            }

            drawHighlightOnLine(g, 0, endColumn, endRow, fontWidth, fontHeight);
        }
    }

    private void drawHighlightOnLine(final Graphics g, final int start, final int end, final int line, final int fontWidth, final int fontHeight) {
        if (end < start) {
            return;
        }
        final FontMetrics metrics = g.getFontMetrics();
        final int startX = start * fontWidth + LEFT_MARGIN_WIDTH;
        final int startY = line * fontHeight + metrics.getDescent();

        final int width = (end - start) * fontWidth;
        g.fillRect(startX, startY, width, fontHeight);
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
        return new Dimension((area.getMaxLineLength() + 1) * fontWidth, (area.getLineCount() + 1) * fontHeight + fontHeight);
    }

    private class DocumentMouseListener extends MouseAdapter {

        @Override
        public void mouseDragged(final MouseEvent event) {
            final int x = event.getX() - LEFT_MARGIN_WIDTH;
            final int y = event.getY();

            final QuickWidthFont font = (QuickWidthFont) area.getFont();

            final int row = y / font.getFixedHeight();
            final int column = x / font.getFixedWidth();

            if (SwingUtilities.isLeftMouseButton(event)) {
                area.getDropCaret().setLocation(row, column);
                area.scrollToDropCaret();
            } else if (SwingUtilities.isMiddleMouseButton(event)) {
                area.getCaret().setLocation(row, column);
                area.scrollToCaret();
            }
            area.repaint();
        }

        @Override
        public void mousePressed(final MouseEvent event) {
            final Caret caret = area.getDropCaret();
            if (caret.isPlaced()) {
                caret.remove();
            }
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
                area.requestFocusInWindow();
                handleCursorPlacement(x - LEFT_MARGIN_WIDTH, y);
            }
        }

        @Override
        public void mouseClicked(final MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
                final QuickWidthFont font = (QuickWidthFont) area.getFont();
                final Caret caret = area.getCaret();
                final Caret dropCaret = area.getDropCaret();
                final int x = event.getX() - LEFT_MARGIN_WIDTH;
                final int y = event.getY();

                final int column = x / font.getFixedWidth();
                final int row = y / font.getFixedHeight();

                final Line line = area.getLine(row);

                int lower = column;
                int higher = column + 1;
                final char[] chars = line.getChars();

                if (column > chars.length) {
                    return;
                }

                while (lower > 0) {
                    final char next = chars[lower - 1];
                    if (!Character.isLetterOrDigit(next) && next != '_') {
                        break;
                    }
                    lower--;
                }

                while (higher < chars.length) {
                    final char next = chars[higher];
                    if (!Character.isLetterOrDigit(next) && next != '_') {
                        break;
                    }
                    higher++;
                }

                caret.setLocation(row, higher);
                dropCaret.setLocation(row, lower);
                area.repaint();
            }
        }

        private void handleCursorPlacement(final int x, final int y) {
            final QuickWidthFont font = (QuickWidthFont) area.getFont();
            area.getCaret().setLocation(y / font.getFixedHeight(), x / font.getFixedWidth());
            area.repaint();
        }

        private void handleCloseBlock(final int y) {
            final QuickWidthFont font = (QuickWidthFont) area.getFont();
            final int fontHeight = font.getFixedHeight();
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

    private class CaretUpAction extends AbstractAction {

        private static final String NAME = "CaretUp";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            if (caret.isPlaced()) {
                caret.moveUp();
                area.scrollToCaret();
            }
        }
    }

    private class CaretDownAction extends AbstractAction {

        private static final String NAME = "CaretDown";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            if (caret.isPlaced()) {
                caret.moveDown();
                area.scrollToCaret();
            }
        }
    }

    private class CaretLeftAction extends AbstractAction {

        private static final String NAME = "CaretLeft";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            if (caret.isPlaced()) {
                caret.moveLeft();
                area.scrollToCaret();
            }
        }
    }

    private class CaretRightAction extends AbstractAction {

        private static final String NAME = "CaretRight";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            if (caret.isPlaced()) {
                caret.moveRight();
                area.scrollToCaret();
            }
        }
    }

    private class SelectAllAction extends AbstractAction {

        private static final String NAME = "SelectAll";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            final Caret dropCaret = area.getDropCaret();

            caret.setLocation(0, 0);
            final int lastLine = area.getLineCount() - 1;
            dropCaret.setLocation(lastLine, area.getLine(lastLine).length());
            area.repaint();
        }
    }

    private class CopyAction extends AbstractAction {

        private static final String NAME = "Copy";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Caret caret = area.getCaret();
            final Caret dropCaret = area.getDropCaret();

            if (!caret.isPlaced() || !dropCaret.isPlaced()) {
                return;
            }

            final int caretRow = caret.getRow();
            final int caretColumn = caret.getColumn();
            final int dropRow = dropCaret.getRow();
            final int dropColumn = dropCaret.getColumn();

            final int startRow;
            final int startColumn;
            final int endRow;
            final int endColumn;

            if (caretRow < dropRow) {
                startRow = caretRow;
                startColumn = caretColumn;
                endRow = dropRow;
                endColumn = dropColumn;
            } else if (caretRow > dropRow) {
                startRow = dropRow;
                startColumn = dropColumn;
                endRow = caretRow;
                endColumn = caretColumn;
            } else {
                startRow = caretRow;
                startColumn = Math.min(caretColumn, dropColumn);
                endRow = caretRow;
                endColumn = Math.max(caretColumn, dropColumn);
            }

            final String text;
            if (startRow == endRow) {
                if (startColumn == endColumn) {
                    return;
                }
                final Line line = area.getLine(startRow);
                if (startColumn > line.length()) {
                    text = "";
                } else {
                    text = line.getText().substring(startColumn, endColumn);
                }
            } else {
                final StringBuilder builder = new StringBuilder();
                final String lineSeparator = System.getProperty("line.separator");

                final Line firstLine = area.getLine(startRow);
                if (firstLine.length() > startColumn) {
                    builder.append(area.getLine(startRow).getText().substring(startColumn));
                }
                builder.append(lineSeparator);

                final List<Line> lines = area.getLines(startRow + 1, endRow - 1);
                for (final Line line : lines) {
                    builder.append(line.getText());
                    builder.append(lineSeparator);
                }
                final Line lastLine = area.getLine(endRow);
                final int end = Math.min(endColumn, lastLine.length());
                builder.append(area.getLine(endRow).getText().substring(0, end));
                text = builder.toString();
            }
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);

        }
    }

    private class PageUpAction extends AbstractAction {

        private static final String NAME = "PageUp";

        @Override
        public void actionPerformed(final ActionEvent e) {
            area.pageUp();
            area.revalidate();
            area.repaint();
        }
    }

    private class PageDownAction extends AbstractAction {

        private static final String NAME = "PageDown";

        @Override
        public void actionPerformed(final ActionEvent e) {
            area.pageDown();
            area.revalidate();
            area.repaint();
        }
    }

    private class ReloadAction extends AbstractAction {

        private static final String NAME = "Reload";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final EditorPanel panel = (EditorPanel) SwingUtilities.getAncestorOfClass(EditorPanel.class, area);
            if (panel == null) {
                return;
            }
            panel.reload();
        }
    }

    private class HardReloadAction extends AbstractAction {

        private static final String NAME = "HardReload";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final EditorPanel panel = (EditorPanel) SwingUtilities.getAncestorOfClass(EditorPanel.class, area);
            if (panel == null) {
                return;
            }
            panel.hardReload();
        }
    }

    private class CloseAction extends AbstractAction {

        private static final String NAME = "Close";

        @Override
        public void actionPerformed(final ActionEvent e) {
            final EditorPanel panel = (EditorPanel) SwingUtilities.getAncestorOfClass(EditorPanel.class, area);
            if (panel == null) {
                return;
            }
            panel.close();
        }

    }
}
