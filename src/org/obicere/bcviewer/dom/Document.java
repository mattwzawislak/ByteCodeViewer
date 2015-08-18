package org.obicere.bcviewer.dom;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * @author Obicere
 */
public class Document {

    private final RootElement root;

    private Element view;

    private int lineHeight;

    private int tabSize = 4;

    private Font font;

    private DocumentContent lastContent;

    private boolean validated = true;

    public Document() {
        this.root = new RootElement(this);
        this.view = root;
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
        invalidate();
    }

    public int getDefaultLineHeight() {
        final FontRenderContext fontRenderContext = new FontRenderContext(null, false, false);
        final Rectangle2D bounds = font.getMaxCharBounds(fontRenderContext);
        return (int) bounds.getHeight();
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public int getTabSize() {
        return tabSize;
    }

    public void setTabSize(final int tabSize) {
        if (tabSize <= 0) {
            throw new IllegalArgumentException("illegal tab size. Must be positive.");
        }
        this.tabSize = tabSize;
        invalidate();
    }

    public void setFont(final Font font) {
        if (font == null) {
            throw new NullPointerException("cannot assign null font.");
        }
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public Element getRoot() {
        return root;
    }

    public DocumentContent getContent() {
        if (validated) {
            return lastContent;
        }
        final DocumentContent content = new DocumentContent(this);

        view.apply(content);

        validate();

        lastContent = content;
        return content;
    }

    public Element getElement(final String name) {
        if (name == null) {
            throw new NullPointerException("cannot find element by null name.");
        }
        final String rootName = root.getName();
        if (name.equals(rootName)) {
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

    public void setView(final Element element) {
        final String qualifiedName = element.getQualifiedName();
        if (getElement(qualifiedName) == null) {
            throw new IllegalArgumentException("cannot set element to view that is not part of document.");
        }
        view = element;
        invalidate();
    }

    public Element setView(final String name) {
        if(name == null){
            throw new NullPointerException("cannot find element by null name.");
        }
        view = getElement(name);
        invalidate();
        return view;
    }

    protected void invalidate() {
        validated = false;
    }

    protected void validate() {
        validated = true;
        root.validate();
    }

    protected boolean isValid() {
        return validated;
    }
}
