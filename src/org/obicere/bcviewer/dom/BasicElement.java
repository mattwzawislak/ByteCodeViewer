package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class BasicElement extends Element {

    private final BasicView view = new BasicView(this);

    public BasicElement(final String name) {
        super(name);
    }

    @Override
    public View<Element> getView() {
        return view;
    }
}
