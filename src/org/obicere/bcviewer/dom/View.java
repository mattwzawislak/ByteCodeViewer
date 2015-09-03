package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Obicere
 */
public abstract class View<E extends Element> {

    protected final E element;

    private Rectangle size;

    private Rectangle childrenSize;

    private Rectangle bounds;

    private final List<View<? extends Element>> childViews;

    public View(final E element) {
        this.element = element;
        this.childViews = new ArrayList<>(element.getChildrenCount());
    }

    public void paint(final Graphics g){
        paintSelf(g, size);
        paintChildren(g);
    }

    protected abstract void paintSelf(final Graphics g, final Rectangle bounds);

    protected void paintChildren(final Graphics g){
        for(final View<? extends Element> view : childViews){
            view.paint(g);
        }
    }


    public Rectangle getSize() {
        return size;
    }

    public Rectangle getChildrenSize() {
        return childrenSize;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle layout(final int x, final int y) {
        this.size = layoutSelf(x, y);

        this.childrenSize = layoutChildren(size);
        return size;
    }

    protected Rectangle layoutChildren(final Rectangle parent) {
        switch (element.getAxis()) {
            case Element.AXIS_LINE:
                return layoutChildrenLine(parent);
            case Element.AXIS_PAGE:
                return layoutChildrenPage(parent);
            default:
                throw new IllegalStateException("invalid axis: " + element.getAxis());
        }
    }

    private Rectangle layoutChildrenPage(final Rectangle parent) {
        final int x = parent.x;
        final int y = parent.y + parent.height;
        int currentHeight = y;
        final List<Element> children = element.getChildren();
        for (final Element child : children) {
            final View<? extends Element> view = child.getView();
            childViews.add(view);

            final Rectangle rectangle = view.layout(x, currentHeight);
            currentHeight += rectangle.getHeight();
        }
        return new Rectangle(x, y, parent.width, currentHeight);
    }

    private Rectangle layoutChildrenLine(final Rectangle parent) {
        final int x = parent.x + parent.width;
        final int y = parent.y;
        int currentWidth = x;
        final List<Element> children = element.getChildren();
        for (final Element child : children) {
            final View<? extends Element> view = child.getView();
            childViews.add(view);

            final Rectangle rectangle = view.layout(currentWidth, y);
            currentWidth += rectangle.getWidth();
        }
        return new Rectangle(x, y, currentWidth, parent.height);
    }

    protected abstract Rectangle layoutSelf(final int x, final int y);
}
