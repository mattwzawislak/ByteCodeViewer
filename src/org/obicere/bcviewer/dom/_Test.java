package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.literals.ParameterDecimalElement;
import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final JDocumentArea documentPane = new JDocumentArea();

        final DocumentBuilder builder = new DocumentBuilder();
        final Document document = new Document(documentPane);

        final Element root = document.getRoot();

        documentPane.setDocument(document);

        for (int i = 0; i < 10; i++) {
            final CollapsibleElement subCollapse = new CollapsibleElement("test" + i, document);
            subCollapse.add(new ParameterDecimalElement(String.valueOf(i), i, builder));
            root.add(subCollapse);

        }

        frame.add(documentPane);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }
}
