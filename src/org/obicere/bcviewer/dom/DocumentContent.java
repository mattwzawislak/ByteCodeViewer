package org.obicere.bcviewer.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Obicere
 */
public class DocumentContent {

    private final List<String> lines = new ArrayList<>();

    private final StringBuilder buffer = new StringBuilder();

    public int getLineCount() {
        if (buffer.length() > 0) {
            // we have a string in the buffer that needs to be considered
            return lines.size() + 1;
        } else {
            return lines.size();
        }
    }

    public String getLine(final int lineNumber) {
        if (lineNumber == lines.size()) {
            return buffer.toString();
        }
        return lines.get(lineNumber);
    }

    public void newline() {
        lines.add(buffer.toString());
        buffer.delete(0, buffer.length());
    }

    public void append(final CharSequence line) {
        if (line == null) {
            throw new NullPointerException("cannot append null line to document content.");
        }
        if (line.length() == 0) {
            return;
        }
        final String[] split = line.toString().split("\n");

        // handle the last buffer up to the next newline
        buffer.append(split[0]);

        // then, only add a new line if there was a newline character
        if (split.length > 1) {
            newline();
        }
        // here, we completely boycott the buffer to improve performance
        // starts at index 1, as the last
        // length - 1 as the last line is put into the buffer
        int i = 1;
        while (i < split.length - 1) {
            final String next = split[i++];
            if (next.length() == 0) {
                lines.add(null);
            } else {
                lines.add(next);
            }
        }
        buffer.append(split[i]);
    }

    public void appendLine(final CharSequence line) {
        append(line);
        newline();
    }
}
