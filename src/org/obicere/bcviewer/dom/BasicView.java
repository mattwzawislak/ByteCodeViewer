package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public class BasicView extends View<Element> {

    public BasicView(final Element element) {
        super(element);
    }

    @Override
    protected void paintSelf(final Graphics g, final Rectangle bounds) {
        // standard view has nothing to paint
    }

    @Override
    protected Rectangle layoutSelf(final int x, final int y) {
        // standard view has no size
        return new Rectangle(x, y, 0, 0);
    }
}
