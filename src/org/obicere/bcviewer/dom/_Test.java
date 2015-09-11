package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.instruction.new_;
import org.obicere.bcviewer.dom.literals.ParameterDecimalElement;
import org.obicere.bcviewer.dom.literals.ParameterStringElement;
import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final JDocumentArea area = new JDocumentArea();

        final DocumentBuilder builder = new DocumentBuilder();
        final Document document = new Document(area);

        final Element root = document.getRoot();

        area.setDocument(document);

        for (int i = 0; i < 30; i++) {
            root.add(new ParameterStringElement("test" + i, "A really long string that I am just making up and wanted to be clever but couldn't think of anything and now I'm sad", builder));
        }

        frame.add(new JScrollPane(area));

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        final DocumentContent content = new DocumentContent();
        root.write(content);
        System.out.println(content.getText());
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }
}
