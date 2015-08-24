package org.obicere.bcviewer.dom;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public class DocumentView implements View {

    private final Document document;

    public DocumentView(final Document document){
        this.document = document;
    }

    @Override
    public void paint(final Graphics g, final Rectangle bounds) {
        if(document == null){
            return;
        }
        g.clipRect(bounds.x, bounds.y, bounds.width, bounds.height);
        document.getRoot().getView().paint(g, bounds);
    }
}
