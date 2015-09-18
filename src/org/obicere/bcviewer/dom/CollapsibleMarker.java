package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 */
public class CollapsibleMarker implements Marker {

    private static final Color PLAIN_COLOR = new Color(0xC0C0C0);

    private static final Color SELECT_COLOR = new Color(0x909090);

    private static final Color TRIGGER_COLOR = new Color(0x707070);

    private static final int COLLAPSIBLE_MARKER_WIDTH = 9; // TODO

    private static final int MINIMIZER_HEIGHT = 9;

    private final CollapsibleElement element;

    private volatile boolean triggered;

    private volatile boolean selected;

    public CollapsibleMarker(final CollapsibleElement element) {
        this.element = element;
    }

    public CollapsibleElement getElement() {
        return element;
    }

    @Override
    public void paint(final Graphics g) {
        final Color color;
        if (triggered) {
            color = TRIGGER_COLOR;
        } else if (selected) {
            color = SELECT_COLOR;
        } else {
            color = PLAIN_COLOR;
        }
        g.setColor(color);

        final Rectangle bounds = getBounds();
        final Rectangle minimizer = getMinimizerBounds();
        g.drawRect(minimizer.x, minimizer.y, minimizer.width, minimizer.height);

        final int halfHeight = (minimizer.height / 2) + minimizer.y;
        final int halfWidth = (minimizer.width / 2) + minimizer.x;
        if (element.isCollapsed()) {
            // draw a +
            g.drawLine(minimizer.x + 2, halfHeight, minimizer.x + minimizer.width - 2, halfHeight);
            g.drawLine(minimizer.y + 2, halfWidth, minimizer.y + minimizer.height - 2, halfWidth);
        } else {
            // draw a -
            g.drawLine(minimizer.x + 2, halfHeight, minimizer.x + minimizer.width - 2, halfHeight);

            final int bottomY = bounds.y + bounds.height - 1;

            // draw the height
            // add the minimizer height to not draw over it
            g.drawLine(halfWidth, bounds.y + minimizer.height, halfWidth, bottomY);

            // finally draw the hook for the bounds
            // this starts at half width and goes to the end
            g.drawLine(halfWidth, bottomY, bounds.width, bottomY);
        }
    }

    @Override
    public Rectangle getBounds() {
        final CollapsibleView view = element.getView();
        if (view == null || !view.isArranged()) {
            return null;
        }
        final Rectangle bounds = view.getBounds();
        return new Rectangle(0, bounds.y, COLLAPSIBLE_MARKER_WIDTH, bounds.height);
    }

    private Rectangle getMinimizerBounds() {
        final CollapsibleView view = element.getView();
        if (view == null || !view.isArranged()) {
            return null;
        }
        final Rectangle bounds = view.getBounds();
        return new Rectangle(0, bounds.y, COLLAPSIBLE_MARKER_WIDTH, MINIMIZER_HEIGHT);
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
        mouseMoved(event);
    }

    @Override
    public void mouseMoved(final MouseEvent event) {
        if (event == null || event.isConsumed()) {
            return;
        }
        final Rectangle minimizer = getMinimizerBounds();
        selected = minimizer.contains(event.getX(), event.getY());
    }

    @Override
    public void mouseExited(final MouseEvent event) {
        triggered = false;
        selected = false;
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        mouseClickHandler(event, true);
        // toggle the state of the collapsible element
        element.setCollapsed(!element.isCollapsed());
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        mouseClickHandler(event, false);
    }

    private void mouseClickHandler(final MouseEvent event, final boolean press) {
        if (event == null || event.isConsumed()) {
            return;
        }
        final Rectangle minimizer = getMinimizerBounds();
        if (minimizer.contains(event.getX(), event.getY())) {
            triggered = press;
        }
    }
}
