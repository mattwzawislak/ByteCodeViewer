package org.obicere.bcviewer.dom;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * @author Obicere
 */
public class Document {

    private final RootElement root;

    private int lineHeight;

    private Font font;

    private DocumentContent lastContent;

    private boolean validated = false;

    public Document(){
        this.root = new RootElement(this);
    }

    public void setLineHeight(final int height) {
        if (height < 0) {
            throw new IllegalArgumentException("line height cannot be set to negative value.");
        }
        if (height == 0) {
            this.lineHeight = getDefaultLineHeight();
        } else {
            this.lineHeight = height;
        }
    }

    public int getDefaultLineHeight() {
        final FontRenderContext fontRenderContext = new FontRenderContext(null, false, false);
        final Rectangle2D bounds = font.getMaxCharBounds(fontRenderContext);
        return (int) bounds.getHeight();
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public Font getFont() {
        return font;
    }

    public Element getRoot() {
        return root;
    }

    public boolean add(final Element element) {
        return root.add(element);
    }

    public boolean addAll(final Element... elements) {
        return root.addAll(elements);
    }

    public boolean addAll(final Iterable<Element> elements) {
        return root.addAll(elements);
    }

    public DocumentContent getContent(){
        final DocumentContent newContent = new DocumentContent();

        validated = true;

        lastContent = newContent;
        return newContent;
    }

    void invalidate(){
        validated = false;
    }
}
