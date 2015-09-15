package org.obicere.bcviewer.dom.ui.swing;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.ui.DocumentRenderer;
import org.obicere.bcviewer.dom.ui.swing.plaf.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 */
public class JDocumentArea extends JComponent implements DocumentRenderer {

    private static final String uiClassID = "DocumentAreaUI";

    private Document document;

    static {
        UIManager.getDefaults().put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea() {
        updateUI();
    }

    public JDocumentArea(final Document document) {
        this.document = document;
        updateUI();
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
