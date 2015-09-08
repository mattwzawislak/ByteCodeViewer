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

    private final ResourcePool<Highlight> highlightPool = new ResourcePool<>();

    private int tabSize = 4;

    private final ReentrantLock lock = new ReentrantLock();

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

    public ResourcePool<Highlight> getHighlightPool() {
        return highlightPool;
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
}
