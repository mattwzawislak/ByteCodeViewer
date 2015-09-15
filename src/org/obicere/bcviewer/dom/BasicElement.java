package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class BasicElement extends Element {

    public BasicElement(final String name) {
        super(name);
    }

    public BasicElement(final String name, final int axis) {
        super(name);
        setAxis(axis);
    }

    @Override
    public View<Element> getView() {
        return new BasicView(this);
    }

    @Override
    public void writeSelf(final DocumentContent content) {
    }
}
