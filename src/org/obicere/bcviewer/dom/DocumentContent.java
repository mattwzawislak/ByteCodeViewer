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

    public void writeLine(final String text){
        builder.append(text);
        builder.append(lineSeparator);
    }

    public void writeLine(){
        builder.append(lineSeparator);
    }

    public int getIndex(final Element element) {
        return indexMap.getOrDefault(element, -1);
    }
}
