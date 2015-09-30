package org.obicere.bcviewer.dom.swing;

import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.swing.ui.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class JDocumentArea extends JComponent {

    private static final String uiClassID = "DocumentAreaUI";

    private QuickWidthFont font;

    private final ArrayList<Block> content = new ArrayList<>();

    private boolean thinCarets = false;

    static {
        UIManager.put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea() {
        updateUI();
    }

    @Override
    public void updateUI() {
        setUI(UIManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public boolean addBlock(final Block block) {
        System.out.println("adding block");
        if (block == null) {
            throw new NullPointerException("block must be non-null");
        }
        System.out.println("added block with linecount: " + block.getLineCount());
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
            final int endOffset = end - block.getLineStart();
            final int startOffset = start - block.getLineStart();
            lines.addAll(block.getLines(startOffset, endOffset));
            return lines;
        } else {
            final Block firstBlock = content.get(startBlockIndex);
            final int startOffset = start - firstBlock.getLineStart();
            lines.addAll(firstBlock.getLines(startOffset, firstBlock.getLineCount() - startOffset));

            for (int index = startBlockIndex + 1; index < endBlockIndex; index++) {
                final Block block = content.get(index);
                lines.addAll(block.getLines());
            }

            final Block lastBlock = content.get(endBlockIndex);
            final int lastBlockEnd = lastBlock.getLineCount() - (end - lastBlock.getLineEnd());
            lines.addAll(lastBlock.getLines(0, lastBlockEnd));
            return lines;
        }
    }

    private int getIndexOfBlockContaining(int lineNumber) {
        for (int i = 0; i < content.size(); i++) {
            final Block block = content.get(i);
            if (block.isVisible()) {
                lineNumber -= block.getLineCount();
                if (lineNumber <= 0) {
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
        for (final Block block : content) {
            if (block.isVisible()) {
                lineNumber -= block.getLineCount();
                if (lineNumber <= 0) {
                    return block;
                }
            }
        }
        return null;
    }

    public int getLineCount() {
        int lines = 0;
        for (final Block block : content) {
            if (block.isVisible()) {
                lines += block.getLineCount();
            }
        }
        return lines;
    }

    public int getMaxLineLength() {
        int max = 0;
        for (final Block block : content) {
            final int nextLength = block.getMaxLineLength();
            if (max < nextLength) {
                max = nextLength;
            }
        }
        return max;
    }

    @Override
    public void setFont(final Font font) {
        if (!(font instanceof QuickWidthFont)) {
            throw new IllegalArgumentException("only supported fonts for the JDocumentArea are QuickWidthFonts");
        }
        final QuickWidthFont qwFont = (QuickWidthFont) font;
        if (!qwFont.isFixedWidth()) {
            throw new IllegalArgumentException("only fixed width fonts are supported");
        }
        final Font oldFont = this.font;
        firePropertyChange("font", oldFont, font);

        this.font = qwFont;
    }

    @Override
    public Font getFont() {
        return font;
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
}
