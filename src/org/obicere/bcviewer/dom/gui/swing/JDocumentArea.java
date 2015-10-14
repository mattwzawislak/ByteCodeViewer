package org.obicere.bcviewer.dom.gui.swing;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.gui.swing.ui.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class JDocumentArea extends JComponent implements DomainAccess {

    private static final String uiClassID = "DocumentAreaUI";

    private final List<Block> content = new ArrayList<>();

    private boolean thinCarets = true;

    private Color highlightColor = new Color(0, 77, 128);

    private final Caret caret     = new Caret(this);
    private final Caret dropCaret = new Caret(this);

    private final Domain domain;

    static {
        UIManager.put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea(final Domain domain) {
        updateUI();
        this.domain = domain;
    }

    @Override
    public void setFont(final Font font) {
        if (font instanceof QuickWidthFont) {
            super.setFont(font);
        }
    }

    @Override
    public void updateUI() {
        setUI(UIManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public Caret getCaret() {
        return caret;
    }

    public Caret getDropCaret() {
        return dropCaret;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(final Color color) {
        if (color == null) {
            return;
        }
        this.highlightColor = color;
    }

    public void scrollToCaret() {
        scrollTo(caret);
    }

    public void scrollToDropCaret() {
        scrollTo(dropCaret);
    }

    private void scrollTo(final Caret caret) {
        final JScrollPane scrollPane = getScrollPaneParent();
        if (scrollPane == null) {
            return;
        }

        final QuickWidthFont font = (QuickWidthFont) domain.getSettingsController().getSettings().getFont("editor.font");
        final JViewport viewport = scrollPane.getViewport();
        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();
        final Point offset = viewport.getViewPosition();

        final int x = caret.getColumn() * fontWidth;
        final int y = caret.getRow() * fontHeight;
        final Rectangle caretRectangle = new Rectangle(x - offset.x, y - offset.y, 1, fontHeight);

        viewport.scrollRectToVisible(caretRectangle);
        revalidate();
        repaint();
    }

    public void pageUp() {
        final QuickWidthFont font = (QuickWidthFont) domain.getSettingsController().getSettings().getFont("editor.font");
        final int delta = -font.getFixedHeight() * 4;
        scroll(delta);
    }

    public void pageDown() {
        final QuickWidthFont font = (QuickWidthFont) domain.getSettingsController().getSettings().getFont("editor.font");
        final int delta = font.getFixedHeight() * 4;
        scroll(delta);
    }

    private void scroll(int delta) {
        final JScrollPane scrollPane = getScrollPaneParent();
        if (scrollPane == null) {
            return;
        }
        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        if (scrollBar == null) {
            return;
        }
        final int value = scrollBar.getValue() + delta;
        final int boundedValue = Math.max(scrollBar.getMinimum(), Math.min(scrollBar.getMaximum(), value));
        scrollBar.setValue(boundedValue);
    }

    private JScrollPane getScrollPaneParent() {
        return (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
    }

    public List<Block> getBlocks() {
        return content;
    }

    // accessed during building and may trip a concurrent mod exception
    public synchronized boolean addBlock(final Block block) {
        if (block == null) {
            throw new NullPointerException("block must be non-null");
        }
        final int start;
        if (content.isEmpty()) {
            start = 0;
        } else {
            start = content.get(content.size() - 1).getLineEnd();
        }
        block.setLineStart(start);
        return content.add(block);
    }

    public Line getLine(final int count) {
        final Block containing = getBlockContaining(count);
        final int offset = count - containing.getLineStart();
        return containing.getLine(offset);
    }

    public List<Line> getLines(final int start, final int end) {
        if (end < start) {
            throw new IllegalArgumentException("end must be >= start");
        }
        final ArrayList<Line> lines = new ArrayList<>(end - start);
        if (start == end) {
            return lines;
        }
        final int startBlockIndex = getIndexOfBlockContaining(start);
        final int endBlockIndex = getIndexOfBlockContaining(end);

        if (startBlockIndex == endBlockIndex) {
            final Block block = content.get(startBlockIndex);

            if (block.isVisible()) {
                final int endOffset = end - block.getLineStart();
                final int startOffset = start - block.getLineStart();
                lines.addAll(block.getLines(startOffset, endOffset));
            }
            return lines;
        } else {
            final Block firstBlock = content.get(startBlockIndex);
            if (firstBlock.isVisible()) {
                final int startOffset = start - firstBlock.getLineStart();
                lines.addAll(firstBlock.getLines(startOffset, firstBlock.getLineCount()));
            }

            for (int index = startBlockIndex + 1; index < endBlockIndex; index++) {
                final Block block = content.get(index);
                if (block.isVisible()) {
                    lines.addAll(block.getLines());
                }
            }

            final Block lastBlock = content.get(endBlockIndex);
            if (lastBlock.isVisible()) {
                final int lastBlockEnd = (end - lastBlock.getLineStart());
                lines.addAll(lastBlock.getLines(0, lastBlockEnd));
            }
            return lines;
        }
    }

    public void setBlockVisible(final Block block, final boolean visible) {
        final int index = content.indexOf(block);
        if (index < 0) {
            return;
        }
        block.setVisible(visible);
        final int delta = visible ? block.getLineCount() : -block.getLineCount();
        for (int i = index + 1; i < content.size(); i++) {
            final Block next = content.get(i);
            next.setLineStart(next.getLineStart() + delta);
        }
    }

    private int getIndexOfBlockContaining(int lineNumber) {
        for (int i = 0; i < content.size(); i++) {
            final Block block = content.get(i);
            if (block.isVisible()) {
                lineNumber -= block.getLineCount();
                if (lineNumber < 0) {
                    return i;
                }
                if (i == content.size() - 1 && lineNumber == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Block getBlockContaining(int lineNumber) {
        if (lineNumber < 0) {
            return null;
        }
        final int index = getIndexOfBlockContaining(lineNumber);
        if (index < 0) {
            return null;
        }
        return content.get(index);
    }

    // can be accessed during building for calculating sizes
    public synchronized int getLineCount() {
        int lines = 0;
        for (final Block block : content) {
            if (block.isVisible()) {
                lines += block.getLineCount();
            }
        }
        return lines;
    }

    // can be accessed during building for calculating sizes
    public synchronized int getMaxLineLength() {
        int max = 0;
        for (final Block block : content) {
            if (block.isVisible()) {
                final int nextLength = block.getMaxLineLength();
                if (max < nextLength) {
                    max = nextLength;
                }
            }
        }
        return max;
    }

    public boolean isThinCarets() {
        return thinCarets;
    }

    public void setThinCarets(final boolean thinCarets) {
        final boolean old = this.thinCarets;
        if (old == thinCarets) {
            return;
        }
        this.thinCarets = thinCarets;
        firePropertyChange("thinCarets", old, thinCarets);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
