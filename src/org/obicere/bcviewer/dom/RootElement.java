package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class RootElement extends Element {

    private static final String ROOT_NAME = "content";

    private final Document document;

    public RootElement(final Document document) {
        super(ROOT_NAME);
        this.document = document;

    }

    @Override
    public void invalidate() {
        document.invalidate();
    }

}
