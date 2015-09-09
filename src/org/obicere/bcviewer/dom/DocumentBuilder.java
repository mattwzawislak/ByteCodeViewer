package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.dom.ui.DocumentRenderer;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class DocumentBuilder {

    private final ColorResourcePool colorPool;

    private final FontResourcePool fontPool;

    private final TextAttributesResourcePool attributesPool;

    private final PaddingCache padding = PaddingCache.getPaddingCache();

    private int tabSize = 5;

    private final ReentrantLock lock = new ReentrantLock();

    public DocumentBuilder() {
        this.colorPool = new ColorResourcePool(this);
        this.fontPool = new FontResourcePool(this);
        this.attributesPool = new TextAttributesResourcePool();

        fontPool.setBaseFont("Courier new", 14);

        attributesPool.updateFonts(fontPool);
        attributesPool.updateColors(colorPool);
    }

    public Document build(final DocumentRenderer renderer, final Modeler<ClassFile> classFileModeler) {
        if (renderer == null) {
            throw new NullPointerException("cannot render document to null renderer");
        }

        if (classFileModeler == null) {
            throw new NullPointerException("cannot model with a null modeler.");
        }
        try {
            lock.lock();
            final Document document = new Document(renderer);

            classFileModeler.model(this, document.getRoot());

            return document;
        } finally {
            lock.unlock();
        }
    }

    void notifyFontChange() {
        attributesPool.updateFonts(fontPool);
    }

    void notifyColorChange() {
        attributesPool.updateColors(colorPool);
    }

    public TextAttributesResourcePool getAttributesPool() {
        return attributesPool;
    }

    public ColorResourcePool getColorPool() {
        return colorPool;
    }

    public FontResourcePool getFontPool() {
        return fontPool;
    }

    public int getTabSize() {
        return tabSize;
    }

    public void setTabSize(final int tabSize) {
        if (tabSize <= 0) {
            throw new IllegalArgumentException("illegal tab size. Must be positive.");
        }
        this.tabSize = tabSize;
    }

    public String getPadding(final int minimum) {
        return getPadding(0, minimum);
    }

    public String getPadding(final int sizeSoFar, final int minimum) {
        return padding.getPadding(getPaddingSize(sizeSoFar, minimum));
    }

    public int getPaddingSize(final int minimum) {
        return getPaddingSize(0, minimum);
    }

    public int getPaddingSize(final int sizeSoFar, final int minimum) {
        if (sizeSoFar < 0) {
            throw new IllegalArgumentException("current size cannot be negative.");
        }
        if (sizeSoFar > minimum) {
            return 0;
        }
        return minimum - sizeSoFar;
    }

    public String getTabbedPadding(final int minimum) {
        return getTabbedPadding(0, minimum);
    }

    public String getTabbedPadding(final int sizeSoFar, final int minimum) {
        return padding.getPadding(getTabbedPaddingSize(sizeSoFar, minimum));
    }

    public int getTabbedPaddingSize(final int minimum) {
        return getTabbedPaddingSize(0, minimum);
    }

    public int getTabbedPaddingSize(final int sizeSoFar, final int minimum) {
        if (sizeSoFar < 0) {
            throw new IllegalArgumentException("current size cannot be negative.");
        }
        final int remainder = minimum % tabSize;
        if (remainder == 0) {
            return minimum - sizeSoFar;
        } else {
            return (minimum - sizeSoFar) + (tabSize - remainder);
        }
    }
}
