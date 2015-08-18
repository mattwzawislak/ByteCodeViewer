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

    public Document() {
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

    public DocumentContent getContent() {
        if (validated) {
            return lastContent;
        }
        final DocumentContent content = new DocumentContent();

        final Element root = getRoot();

        root.apply(content);
        root.validate();

        validated = true;
        lastContent = content;
        return content;
    }

    public Element getElement(final String name) {
        if (name == null) {
            throw new NullPointerException("cannot find element by null name.");
        }
        final String rootName = root.getName();
        if(name.equals(rootName)){
            return root;
        }
        if (name.startsWith(rootName)) {
            // add 1 to also remove the '.' after the root's name
            final String removed = name.substring(rootName.length() + 1);
            return root.getElement(removed);
        } else {
            return root.getElement(name);
        }
    }

    void invalidate() {
        validated = false;
    }
}
