package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.awt.Query;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Document {

    private final List<Block> content;

    public Document(final List<Block> content) {
        this.content = content;
    }

    public Query query(final String input, final boolean ignoreCase, final boolean regex) {
        return new Query(this, input, ignoreCase, regex);
    }

    public List<Block> getBlocks() {
        return content;
    }

    public Line getLine(final int count) {
        final Block containing = getBlockContaining(count);
        final int offset = count - containing.getLineStart();
        return containing.getLine(offset);
    }

    public List<Line> getLines() {
        return getLines(0, getLineCount());
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
            if (block.isVisible()) {
                final int nextLength = block.getMaxLineLength();
                if (max < nextLength) {
                    max = nextLength;
                }
            }
        }
        return max;
    }
}
