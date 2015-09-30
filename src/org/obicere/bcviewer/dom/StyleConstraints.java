package org.obicere.bcviewer.dom;

import java.util.Iterator;
import java.util.LinkedList;

/**
 */
public class StyleConstraints {

    private final LinkedList<Style> styles = new LinkedList<>();

    private final LinkedList<Integer> bounds = new LinkedList<>();

    private boolean open = true;

    private Style lastStyle;

    private int lastLength;

    public StyleConstraints() {
        bounds.add(0);
    }

    public void addConstraint(final Style style, final int length) {
        if (!open) {
            return;
        }
        if (style == null || style == lastStyle) {
            lastLength += length;
            return;
        }
        publishLast();
        lastStyle = style;
        lastLength = lastLength + length;
    }

    private void publishLast() {
        styles.add(lastStyle);
        bounds.add(lastLength);
    }

    public void close() {
        this.open = false;
        publishLast();
    }

    public Iterator<Integer> boundsIterator() {
        return bounds.iterator();
    }

    public Iterator<Style> styleIterator() {
        return styles.iterator();
    }

}
