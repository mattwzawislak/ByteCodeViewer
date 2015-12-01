package org.obicere.bytecode.viewer.dom.style;

import java.util.LinkedList;
import java.util.List;

/**
 */
public class StyleConstraints {

    private List<Style> styles = new LinkedList<>();

    private List<Integer> bounds = new LinkedList<>();

    private Style[] styleArray;

    private int[] boundsArray;

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

        this.styleArray = styles.toArray(new Style[styles.size()]);
        this.boundsArray = bounds.stream().mapToInt(e -> e).toArray();

        styles = null;
        bounds = null;
    }

    public Style[] getStyles() {
        return styleArray;
    }

    public int[] getBounds() {
        return boundsArray;
    }

}
