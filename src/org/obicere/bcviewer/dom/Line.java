package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.style.StyleConstraints;

/**
 */
public class Line {

    private final StyleConstraints constraints;

    private final char[] chars;

    public Line(final StyleConstraints constraints, final char[] chars) {
        this.constraints = constraints;
        this.chars = chars;
    }

    public char[] getChars() {
        return getChars(0, chars.length);
    }

    public char[] getChars(final int start, final int end) {
        if (start == end) {
            return new char[0];
        }
        if (end < start) {
            throw new IllegalArgumentException("end must be >= start");
        }
        final int length = (end - start);
        final char[] copy = new char[length];
        System.arraycopy(chars, start, copy, 0, length);
        return copy;
    }

    public String getText() {
        return new String(chars);
    }

    public StyleConstraints getConstraints() {
        return constraints;
    }

    public int length() {
        return chars.length;
    }
}
