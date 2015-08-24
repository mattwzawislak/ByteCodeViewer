package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Obicere
 */
public class DocumentBuilder {

    private final ResourcePool<Color> colorPool = new ResourcePool<>();

    private final ResourcePool<Font> fontPool = new ResourcePool<>();

    private final ResourcePool<Highlight> highlightPool = new ResourcePool<>();

    public Document build(final Modeler<ClassFile> classFileModeler) {
        final Document document = new Document();

        classFileModeler.model(this, document.getRoot());

        return document;
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
}
