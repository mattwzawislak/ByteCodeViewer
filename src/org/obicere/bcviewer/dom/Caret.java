package org.obicere.bcviewer.dom;

/**
 */
public class Caret {

    private final Element element;
    private final int     index;

    public Caret(final Element element, final int index) {
        if (element == null) {
            throw new NullPointerException("caret element cannot be null.");
        }
        this.element = element;
        this.index = index;
    }

    public Element getElement() {
        return element;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(getClass().getName());
        builder.append("[element=");
        builder.append(element);
        builder.append(",index=");
        builder.append(index);
        builder.append(']');
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 29;
        hash = hash * 31 + element.hashCode();
        hash = hash * 31 + index;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Caret) {
            final Caret caret = (Caret) obj;
            return caret.getIndex() == getIndex() && caret.getElement().equals(getElement());
        }
        return false;
    }
}
