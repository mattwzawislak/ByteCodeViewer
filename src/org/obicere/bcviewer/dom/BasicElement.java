package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class BasicElement extends Element {

    public BasicElement(final String name) {
        super(name);
    }

    @Override
    public View<Element> getView() {
        return new BasicView(this);
    }

    @Override
    public void writeSelf(final DocumentContent content) {
    }
}
