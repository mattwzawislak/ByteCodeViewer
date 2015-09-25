package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 */
public class CollapsibleView extends View<CollapsibleElement> {

    private final Rectangle collapsed = new Rectangle();

    public CollapsibleView(final CollapsibleElement element) {
        super(element);
    }

    @Override
    protected void paintSelf(final Graphics g, final Rectangle bounds) {

    }

    @Override
    protected void paintChildren(final Graphics g) {
        if (!element.isCollapsed()) {
            super.paintChildren(g);
        }
    }

    @Override
    protected Rectangle layoutSelf(final int x, final int y) {
        return new Rectangle(x, y, 0, 0);
    }

    @Override
    protected Rectangle layoutChildren(final Rectangle parent) {
        if (element.isCollapsed()) {
            collapsed.x = parent.x;
            collapsed.y = parent.y;
            return collapsed;
        } else {
            return super.layoutChildren(parent);
        }
    }
}
