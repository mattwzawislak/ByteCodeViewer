package org.obicere.bcviewer.dom;

/**
 */
public class ResourceTextAttributes extends TextAttributes {

    private final String fontName;
    private final String colorName;

    public ResourceTextAttributes(final String fontName, final String colorName) {
        this.fontName = fontName;
        this.colorName = colorName;
    }

    public void updateFont(final FontResourcePool resourcePool){
        setFont(resourcePool.get(fontName));
    }

    public void updateColor(final ColorResourcePool resourcePool){
        setColor(resourcePool.get(colorName));
    }

}
