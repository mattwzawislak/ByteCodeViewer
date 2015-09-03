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


    // TODO make these a bit more tangible
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

        final Rectangle bounds = new Rectangle();
        bounds.x = Math.min(size.x, childrenSize.x);
        bounds.y = Math.min(size.y, childrenSize.y);
        bounds.width = Math.max(size.width, childrenSize.width);
        bounds.height = Math.max(size.height, childrenSize.height);
        return bounds;
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
        int currentWidth = 0;
        final List<Element> children = element.getChildren();
        for (final Element child : children) {
            final View<? extends Element> view = child.getView();
            childViews.add(view);

            final Rectangle rectangle = view.layout(x, currentHeight);
            currentHeight += rectangle.getHeight();
            currentWidth = Math.max(rectangle.width, currentWidth);
        }
        // subtract initial y to counter parent's offset
        // TODO: prove that this patch actually worked
        return new Rectangle(x, y, currentWidth, currentHeight - y);
    }

    private Rectangle layoutChildrenLine(final Rectangle parent) {
        final int x = parent.x + parent.width;
        final int y = parent.y;
        int currentWidth = x;
        int currentHeight = 0;
        final List<Element> children = element.getChildren();
        for (final Element child : children) {
            final View<? extends Element> view = child.getView();
            childViews.add(view);

            final Rectangle rectangle = view.layout(currentWidth, y);
            currentWidth += rectangle.getWidth();
            currentHeight = Math.max(rectangle.height, currentHeight);
        }
        // subtract initial x to counter parent's offset
        // TODO: prove that this patch actually worked
        return new Rectangle(x, y, currentWidth - x, currentHeight);
    }

    protected abstract Rectangle layoutSelf(final int x, final int y);
}
