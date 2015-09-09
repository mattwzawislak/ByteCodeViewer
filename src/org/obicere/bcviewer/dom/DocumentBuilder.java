package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.dom.ui.DocumentRenderer;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class DocumentBuilder {

    private final ResourcePool<Color> colorPool = new ResourcePool<>();

    private final ResourcePool<Font> fontPool = new ResourcePool<>();

    private final PaddingCache padding = PaddingCache.getPaddingCache();

    private int tabSize = 4;

    private final ReentrantLock lock = new ReentrantLock();

    private String baseFontName;

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

    public ResourcePool<Color> getColorPool() {
        return colorPool;
    }

    public ResourcePool<Font> getFontPool() {
        return fontPool;
    }

    public void setBaseFont(final String name, final int size) {
        this.baseFontName = name;

        loadBaseline(name, size);
        loadSubscript(name, size);
        loadSuperscript(name, size);
    }

    private void loadSuperscript(final String name, final int size) {
        final String plain = "superscript.plain";
        final String bold = "superscript.bold";
        final String italic = "superscript.italic";
        final String boldItalic = "superscript.boldItalic";

        final int fixedSize = (int) (size * Script.SUPERSCRIPT.getSize());

        fontPool.add(plain, new Font(name, Font.PLAIN, fixedSize));
        fontPool.add(bold, new Font(name, Font.BOLD, fixedSize));
        fontPool.add(italic, new Font(name, Font.ITALIC, fixedSize));
        fontPool.add(boldItalic, new Font(name, Font.BOLD | Font.ITALIC, fixedSize));
    }

    private void loadSubscript(final String name, final int size) {
        final String plain = "subscript.plain";
        final String bold = "subscript.bold";
        final String italic = "subscript.italic";
        final String boldItalic = "subscript.boldItalic";

        final int fixedSize = (int) (size * Script.SUBSCRIPT.getSize());

        fontPool.add(plain, new Font(name, Font.PLAIN, fixedSize));
        fontPool.add(bold, new Font(name, Font.BOLD, fixedSize));
        fontPool.add(italic, new Font(name, Font.ITALIC, fixedSize));
        fontPool.add(boldItalic, new Font(name, Font.BOLD | Font.ITALIC, fixedSize));
    }

    private void loadBaseline(final String name, final int size) {
        final String plain = "baseline.plain";
        final String bold = "baseline.bold";
        final String italic = "baseline.italic";
        final String boldItalic = "baseline.boldItalic";

        final int fixedSize = (int) (size * Script.BASELINE.getSize());

        fontPool.add(plain, new Font(name, Font.PLAIN, fixedSize));
        fontPool.add(bold, new Font(name, Font.BOLD, fixedSize));
        fontPool.add(italic, new Font(name, Font.ITALIC, fixedSize));
        fontPool.add(boldItalic, new Font(name, Font.BOLD | Font.ITALIC, fixedSize));
    }

    public String getBaseFontName() {
        return baseFontName;
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
