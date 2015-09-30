package org.obicere.bcviewer.dom;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Block {

    private final ArrayList<Line> lines = new ArrayList<>();

    private volatile int maxLineLength = 0;

    private volatile boolean visible = true;

    private int lineStart = 0;

    private boolean collapsible;

    public int getLineCount() {
        return lines.size();
    }

    public Block() {
        this(false);
    }

    public Block(final boolean collapsible) {
        this.collapsible = collapsible;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public void setLineStart(final int lineStart) {
        if (lineStart < 0) {
            throw new IllegalArgumentException("can not have negative line starts.");
        }
        this.lineStart = lineStart;
    }

    public int getLineStart() {
        return lineStart;
    }

    public int getLineEnd() {
        return lineStart + lines.size();
    }

    public Line getLine(final int count) {
        return lines.get(count);
    }

    public void addLine(final Line line) {
        if (line == null) {
            throw new NullPointerException("line must be non-null.");
        }
        final int length = line.length();
        if (length > maxLineLength) {
            maxLineLength = length;
        }
        lines.add(line);
    }

    public int getMaxLineLength() {
        return maxLineLength;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public List<Line> getLines() {
        return getLines(0, getLineCount());
    }

    public List<Line> getLines(final int start, final int end) {
        if (end <= start) {
            return new ArrayList<>();
        }
        return lines.subList(start, end);
    }
}
