package org.obicere.bcviewer.dom;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class DocumentContent {

    private final StringBuilder builder = new StringBuilder();

    private final Map<Element, Integer> indexMap = new HashMap<>();

    private final String lineSeparator = System.getProperty("line.separator");

    public void write(final Element element, final String content) {
        final int index = builder.length();

        builder.append(content);
        indexMap.put(element, index);
    }

    public void writeLine(final Element element, final String content) {
        write(element, content);
        builder.append(lineSeparator);
    }

    public void write(final String text) {
        builder.append(text);
    }

    public void writeLine(final String text) {
        builder.append(text);
        builder.append(lineSeparator);
    }

    public void writeLine() {
        builder.append(lineSeparator);
    }

    public int getIndex(final Element element) {
        return indexMap.getOrDefault(element, -1);
    }

    public boolean isEmpty() {
        return builder.length() == 0;
    }

    public void trimToSize() {
        builder.trimToSize();
    }

    public String getText() {
        return builder.toString();
    }

    public String getText(final int start) {
        return builder.substring(start);
    }

    public String getText(final int start, final int end) {
        return builder.substring(start, end);
    }

    public String getText(final Caret start, final Caret end) {
        final int startIndex = getIndex(start.getElement());
        if (startIndex == -1) {
            return null;
        }
        final int endIndex = getIndex(end.getElement());
        if (endIndex == -1) {
            return null;
        }
        return getText(startIndex + start.getIndex(), endIndex + end.getIndex());
    }
}
