package org.obicere.bcviewer.dom;

/**
 */
public class Selection {

    private final Caret start;
    private final Caret end;

    public Selection(final Caret start, final Caret end) {
        if (start == null) {
            throw new NullPointerException("start of selection cannot be null.");
        }
        if (end == null) {
            throw new NullPointerException("end of selection cannot be null.");
        }
        this.start = start;
        this.end = end;
    }

    public Caret getStart() {
        return start;
    }

    public Caret getEnd() {
        return end;
    }

    public boolean isSingleElement() {
        return start.getElement() == end.getElement();
    }

    public boolean isEmptySelection() {
        return start.equals(end);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(getClass().getName());
        builder.append("[start=");
        builder.append(start);
        builder.append(",end=");
        builder.append(end);
        builder.append(']');
        return builder.toString();
    }

}
