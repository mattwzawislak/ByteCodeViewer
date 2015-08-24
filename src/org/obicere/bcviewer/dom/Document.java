package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class Document {

    private static final String ROOT_NAME = "root";

    private final Element root;

    // change this to a View?
    private Element display;

    private boolean validated = true;

    public Document() {
        this.root = new Element(ROOT_NAME);
        this.display = root;
    }

    /*
    TODO: move this to an appropriate modelling class for the documents
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
    } */

    public Element getRoot() {
        return root;
    }

    public Element getElement(final String name) {
        if (name == null) {
            throw new NullPointerException("cannot find element by null name.");
        }
        if (name.equals(ROOT_NAME)) {
            return root;
        }
        if (name.startsWith(ROOT_NAME)) {
            // add 1 to also remove the '.' after the root's name
            final String removed = name.substring(ROOT_NAME.length() + 1);
            return root.getElement(removed);
        } else {
            return root.getElement(name);
        }
    }

    public void setDisplay(final Element element) {
        final String qualifiedName = element.getQualifiedName();
        if (getElement(qualifiedName) == null) {
            throw new IllegalArgumentException("cannot set element to display that is not part of document.");
        }
        display = element;
    }

    public Element setDisplay(final String name) {
        if(name == null){
            throw new NullPointerException("cannot find element by null name.");
        }
        display = getElement(name);
        return display;
    }
}
