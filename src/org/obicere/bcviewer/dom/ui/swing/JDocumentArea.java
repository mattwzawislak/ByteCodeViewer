package org.obicere.bcviewer.dom.ui.swing;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.Modeler;
import org.obicere.bcviewer.dom.ui.DocumentRenderer;
import org.obicere.bcviewer.dom.ui.swing.plaf.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 */
public class JDocumentArea extends JComponent implements DocumentRenderer {

    private static final String uiClassID = "DocumentAreaUI";

    private Document document;

    private boolean markerPaneVisible = true;

    private final DocumentBuilder builder;

    static {
        UIManager.getDefaults().put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea(final DocumentBuilder builder) {
        this.builder = builder;
        updateUI();
    }

    public boolean isMarkerPaneVisible() {
        return markerPaneVisible;
    }

    public void setMarkerPaneVisible(final boolean visible) {
        this.markerPaneVisible = visible;
    }

    @Override
    public Font getFont() {
        return builder.getFontPool().get(FontResourcePool.FONT_BASELINE_PLAIN);
    }

    @Override
    public void setFont(final Font font) {
        setFont(font.getName(), font.getSize());
    }

    public void setFont(final String name, final int size) {
        builder.getFontPool().setBaseFont(name, size);
    }

    public int getTabSize() {
        return builder.getTabSize();
    }

    public void setTabSize(final int tabSize) {
        builder.setTabSize(tabSize);
    }

    public Document setContent(final ClassFile classFile, final Modeler classFileModeler) {
        final Document document = builder.build(this, classFile, classFileModeler);
        setDocument(document);

        return document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(final Document document) {
        if (this.document == document) {
            return;
        }
        this.document = document;
        invalidate();
    }

    @Override
    public void updateUI() {
        setUI(UIManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    @Override
    public Rectangle getViewport() {
        final Insets insets = getInsets();
        final Rectangle bounds = getBounds();
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        return bounds;
    }

    @Override
    public void scrollToVisible(final Rectangle rectangle) {

    }
}
