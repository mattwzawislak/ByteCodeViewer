package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Obicere
 */
public abstract class View<E extends Element> {

    protected static final Rectangle EMPTY_RECTANGLE = new Rectangle();

    protected final E element;

    private Rectangle size;

    private final Rectangle childrenSize = new Rectangle();

    private final Rectangle bounds = new Rectangle();

    private final List<View<? extends Element>> childViews;

    private boolean isArranged = false;

    public View(final E element) {
        this.element = element;
        this.childViews = new ArrayList<>(element.getChildrenCount());
    }

    public boolean isArranged() {
        return isArranged;
    }

    public E getElement() {
        return element;
    }

    public List<View<? extends Element>> getChildViews() {
        return childViews;
    }

    public void paint(final Graphics g) {
        paintSelf(g, size);
        paintChildren(g);
    }

    protected abstract void paintSelf(final Graphics g, final Rectangle bounds);

    protected void paintChildren(final Graphics g) {
        for (final View<? extends Element> view : childViews) {
            if (view.bounds.intersects(g.getClipBounds())) {
                view.paint(g);
            }
        }
    }

    public Rectangle getSize() {
        if (!isArranged) {
            return EMPTY_RECTANGLE;
        }
        return size;
    }

    public Rectangle getChildrenSize() {
        if (!isArranged) {
            return EMPTY_RECTANGLE;
        }
        return childrenSize;
    }

    public Rectangle getBounds() {
        if (!isArranged) {
            return EMPTY_RECTANGLE;
        }
        return bounds;
    }

    public Rectangle layout(final int x, final int y) {
        this.size = layoutSelf(x, y);

        layoutChildren(size);

        bounds.x = Math.min(size.x, childrenSize.x);
        bounds.y = Math.min(size.y, childrenSize.y);
        if (element.getAxis() == Element.AXIS_LINE) {
            bounds.width = (size.width + childrenSize.width);
        } else {
            bounds.width = Math.max(size.width, childrenSize.width);
        }

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
        // be sure to clear the child views
        childViews.clear();

        switch (element.getAxis()) {
            case Element.AXIS_LINE:
                layoutChildrenLine(parent);
                return childrenSize;
            case Element.AXIS_PAGE:
                layoutChildrenPage(parent);
                return childrenSize;
            default:
                throw new IllegalStateException("invalid axis: " + element.getAxis());
        }
    }

    private void layoutChildrenPage(final Rectangle parent) {
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
        childrenSize.x = x;
        childrenSize.y = y;
        childrenSize.width = currentWidth;
        childrenSize.height = currentHeight - y;
    }

    private void layoutChildrenLine(final Rectangle parent) {
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
        childrenSize.x = x;
        childrenSize.y = y;
        childrenSize.width = currentWidth - x;
        childrenSize.height = currentHeight;
    }
}
