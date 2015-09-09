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

    private boolean isArranged = false;

    public View(final E element) {
        this.element = element;
        this.childViews = new ArrayList<>(element.getChildrenCount());
    }

    public void paint(final Graphics g) {
        paintSelf(g, size);
        paintChildren(g);
    }

    protected abstract void paintSelf(final Graphics g, final Rectangle bounds);

    protected void paintChildren(final Graphics g) {
        for (final View<? extends Element> view : childViews) {
            final Graphics childGraphics = g.create();
            view.paint(childGraphics);
            childGraphics.dispose();
        }
    }

    public Rectangle getSize() {
        if (!isArranged) {
            return new Rectangle();
        }
        return size;
    }

    public Rectangle getChildrenSize() {
        if (!isArranged) {
            return new Rectangle();
        }
        return childrenSize;
    }

    public Rectangle getBounds() {
        if (!isArranged) {
            return new Rectangle();
        }
        return bounds;
    }

    public Rectangle layout(final int x, final int y) {
        childViews.clear();

        this.size = layoutSelf(x, y);

        this.childrenSize = layoutChildren(size);

        this.bounds = new Rectangle();
        bounds.x = Math.min(size.x, childrenSize.x);
        bounds.y = Math.min(size.y, childrenSize.y);
        bounds.width = (size.width + childrenSize.width);

        // special case scenario - the parent's height also needs to be
        // added as an extra line is added regardless of the size or
        // presence of children
        if (element.getAxis() == Element.AXIS_PAGE) {
            bounds.height = (size.height + childrenSize.height);
        } else {
            bounds.height = Math.max(size.height, childrenSize.height);
        }


        isArranged = true;

        return bounds;
    }

    protected abstract Rectangle layoutSelf(final int x, final int y);

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
        return new Rectangle(x, y, currentWidth - x, currentHeight);
    }
}
