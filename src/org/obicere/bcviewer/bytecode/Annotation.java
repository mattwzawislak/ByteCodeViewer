package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class Annotation {

    private final ElementValuePair[] elementValuePairs;

    public Annotation(final ElementValuePair[] elementValuePairs) {

        if (elementValuePairs == null) {
            throw new NullPointerException("element value pairs not defined.");
        }

        this.elementValuePairs = elementValuePairs;
    }

    public ElementValuePair[] getElementValuePairs() {
        return elementValuePairs;
    }

}
