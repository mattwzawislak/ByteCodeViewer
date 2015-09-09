package org.obicere.bcviewer.dom;

/**
 */
public class CollapsibleElement extends Element {

    private boolean collapsed = false;

    private final CollapsibleView view = new CollapsibleView(this);

    public CollapsibleElement(final String name) {
        super(name);
    }

    public void setCollapsed(final boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    @Override
    public CollapsibleView getView() {
        return view;
    }
}
