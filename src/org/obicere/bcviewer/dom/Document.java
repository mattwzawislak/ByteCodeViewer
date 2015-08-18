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

    private int tabSize = 4;

    private Font font;

    private DocumentContent lastContent;

    private boolean validated = true;

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
        if(tabSize <= 0){
            throw new IllegalArgumentException("illegal tab size. Must be positive.");
        }
        this.tabSize = tabSize;
        invalidate();
    }

    public Font getFont() {
        return font;
    }

    public Element getRoot() {
        return root;
    }

    public boolean add(final Element element) {
        final boolean result = root.add(element);
        if(result){
            invalidate();
        }
        return result;
    }

    public boolean addAll(final Element... elements) {
        final boolean result = root.addAll(elements);
        if(result){
            invalidate();
        }
        return result;
    }

    public boolean addAll(final Iterable<Element> elements) {
        final boolean result = root.addAll(elements);
        if(result){
            invalidate();
        }
        return result;
    }

    public DocumentContent getContent() {
        if (validated) {
            return lastContent;
        }
        final DocumentContent content = new DocumentContent(this);

        root.apply(content);

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

    protected void invalidate() {
        validated = false;
    }

    protected void validate(){
        validated = true;
        root.validate();
    }
}
