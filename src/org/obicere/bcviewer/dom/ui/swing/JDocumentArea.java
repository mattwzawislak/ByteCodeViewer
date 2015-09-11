package org.obicere.bcviewer.dom.ui.swing;

import org.obicere.bcviewer.dom.Document;

import javax.swing.JComponent;

/**
 */
public class JDocumentArea extends JComponent {

    private Document document;

    public JDocumentArea() {

    }

    public JDocumentArea(final Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

}
