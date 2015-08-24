package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public interface View {

    public void doPaint(final Graphics g, final Rectangle bounds);

}
