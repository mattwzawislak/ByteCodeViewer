package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 */
public interface Marker {

    public void paint(final Graphics g);

    public Rectangle getBounds();

    public void mouseEntered(final MouseEvent event);

    public void mouseExited(final MouseEvent event);

    public void mouseClicked(final MouseEvent event);

}
