package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ElementValuePair {

    private final int          elementNameIndex;
    private final ElementValue value;

    public ElementValuePair(final int elementNameIndex, final ElementValue value) {

        if (value == null) {
            throw new NullPointerException("value is not defined.");
        }

        this.elementNameIndex = elementNameIndex;
        this.value = value;
    }

    public int getElementNameIndex() {
        return elementNameIndex;
    }

    public ElementValue getValue() {
        return value;
    }

}
