package org.obicere.bcviewer.dom.ui;

import java.awt.Rectangle;

/**
 */
public interface DocumentRenderer {

    public Rectangle getViewport();

    public void scrollToVisible(final Rectangle rectangle);

}
