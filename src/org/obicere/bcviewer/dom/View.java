package org.obicere.bcviewer.dom;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public interface View {

    public void paint(final Graphics g, final Rectangle bounds);

    public Dimension getSize();

}
