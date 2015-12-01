package org.obicere.bytecode.viewer.dom;

import org.obicere.bytecode.viewer.dom.style.StyleConstraints;

/**
 */
public class Line {

    private final StyleConstraints constraints;

    private final String chars;

    public Line(final StyleConstraints constraints, final String chars) {
        this.constraints = constraints;
        this.chars = chars;
    }

    public char[] getChars() {
        return chars.toCharArray();
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
        chars.getChars(start, end, copy, 0);
        return copy;
    }

    public String getText() {
        return chars;
    }

    public StyleConstraints getConstraints() {
        return constraints;
    }

    public int length() {
        return chars.length();
    }
}
