package org.obicere.bcviewer.dom;

/**
 */
public class RootElement extends BasicElement {

    private static final String ROOT_NAME = "root";

    private final Document document;

    public RootElement(final Document document) {
        super(ROOT_NAME);
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    void addedTo(final Element parent) {
        throw new UnsupportedOperationException("cannot add root element to any other element");
    }
}
