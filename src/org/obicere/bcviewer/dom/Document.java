package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.awt.Query;
import org.obicere.bcviewer.dom.awt.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 */
public class Document {

    private final List<Block> content;

    private QueryProcessor processor = new QueryProcessor();

    private Thread query;

    private ReentrantLock queryLock = new ReentrantLock();

    public Document(final List<Block> content) {
        this.content = content;
    }

    public Query query(String input, final boolean ignoreCase, final boolean regex) {
        try {
            queryLock.lock();
            if (query != null) {
                query.interrupt();
            }
            processor.result = new Query();
            processor.input = input;
            processor.ignoreCase = ignoreCase;
            processor.regex = regex;

            query = new Thread(processor);
            query.start();

            return processor.result;
        } finally {
            queryLock.unlock();
        }
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

    private class QueryProcessor implements Runnable {

        private volatile Query   result;
        private volatile String  input;
        private volatile boolean ignoreCase;
        private volatile boolean regex;

        @Override
        public void run() {
            if (input == null || input.length() == 0) {
                return;
            }
            final List<Line> lines = getLines();
            if (regex) {
                queryRegex(lines, input, ignoreCase);
            }
            if (ignoreCase) {
                input = input.toLowerCase();
            }
            if (input.contains("\n")) {
                queryWithLineBreaks(lines, input, ignoreCase);
            } else {
                queryNoLineBreaks(lines, input, ignoreCase);
            }
        }

        private void queryRegex(final List<Line> lines, final String input, final boolean ignoreCase) {
            final Pattern pattern;
            try {
                pattern = Pattern.compile(input);
            } catch (final PatternSyntaxException e) {
                return;
            }
            final int size = lines.size();
            for (int i = 0; i < size; i++) {
                if (Thread.interrupted()) {
                    return;
                }
                final Line line = lines.get(i);
                String text = line.getText();
                if (ignoreCase) {
                    text = text.toLowerCase();
                }
                final Matcher matcher = pattern.matcher(text);

                while (matcher.find()) {
                    addResult(i, i, matcher.start(), matcher.end());
                }
            }
        }

        private void queryNoLineBreaks(final List<Line> lines, String input, final boolean ignoreCase) {
            final int size = lines.size();
            for (int i = 0; i < size; i++) {
                if (Thread.interrupted()) {
                    return;
                }
                final Line line = lines.get(i);
                String text = line.getText();
                if (ignoreCase) {
                    text = text.toLowerCase();
                }

                int index = 0;
                while ((index = text.indexOf(input, index)) >= 0) {
                    addResult(i, i, index, index + input.length());
                    index++;
                }
            }
        }

        private void queryWithLineBreaks(final List<Line> lines, String input, final boolean ignoreCase) {
            final int size = lines.size();

            final String[] parts = input.split("\n");
            if (parts.length == 0) {
                return;
            }

            for (int i = 0; i < size - 1; i++) {
                if (Thread.interrupted()) {
                    return;
                }
                String text = lines.get(i).getText();
                if (ignoreCase) {
                    text = text.toLowerCase();
                }

                if (!text.endsWith(parts[0])) {
                    continue;
                }
                final int start = text.length() - parts[0].length();

                int j = 1;
                boolean valid = true;
                for (; j < parts.length - 1 && (j + i) < size; j++) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    text = lines.get(j + i).getText();
                    if (ignoreCase) {
                        text = text.toLowerCase();
                    }
                    if (!parts[j].equals(text)) {
                        valid = false;
                        break;
                    }

                }
                if (!valid) {
                    continue;
                }

                text = lines.get(i + j).getText();
                if (ignoreCase) {
                    text = text.toLowerCase();
                }

                final String last = parts[parts.length - 1];
                if (text.startsWith(last)) {
                    final int end = last.length();
                    addResult(i, i + j, start, end);
                }
            }
        }

        private void addResult(final int startLine, final int endLine, final int start, final int end) {
            result.addResult(new QueryResult(startLine, endLine, start, end));
        }
    }
}
