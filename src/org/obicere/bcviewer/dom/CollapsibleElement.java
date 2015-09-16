package org.obicere.bcviewer.dom;

/**
 */
public class CollapsibleElement extends Element {

    private boolean collapsed = false;

    private final CollapsibleView view = new CollapsibleView(this);

    public CollapsibleElement(final String name, final Document builder) {
        super(name);
        builder.addMarker(new CollapsibleMarker(this));
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

    @Override
    protected void writeChildren(final DocumentContent content) {
        if (!isCollapsed()) {
            super.writeChildren(content);
        }
    }

    @Override
    protected void writeSelf(final DocumentContent content) {

    }
}
