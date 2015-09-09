package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.instruction.aaload;
import org.obicere.bcviewer.bytecode.instruction.aastore;
import org.obicere.bcviewer.bytecode.instruction.aconst_null;
import org.obicere.bcviewer.bytecode.instruction.aload;
import org.obicere.bcviewer.bytecode.instruction.multianewarray;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final TextAttributes attributes = new TextAttributes();
        attributes.setFont(new Font("Courier new", Font.PLAIN, 20));
        attributes.setColor(new Color(227, 80, 0));

        final DocumentBuilder builder = new DocumentBuilder();
        final Document document = new Document(() -> frame.getContentPane().getBounds());

        final Element root = document.getRoot();

        final aaload ins1 = new aaload();
        final aastore ins2 = new aastore();
        final aconst_null ins3 = new aconst_null();
        final aload ins4 = new aload(100);
        final multianewarray ins5 = new multianewarray(255, 0, 10);

        final BasicElement line1 = new BasicElement("line1");
        final BasicElement line2 = new BasicElement("line2");
        final BasicElement line3 = new BasicElement("line3");
        final BasicElement line4 = new BasicElement("line4");
        final BasicElement line5 = new BasicElement("line5");

        line1.setAxis(Element.AXIS_LINE);
        line2.setAxis(Element.AXIS_LINE);
        line3.setAxis(Element.AXIS_LINE);
        line4.setAxis(Element.AXIS_LINE);
        line5.setAxis(Element.AXIS_LINE);

        ins1.model(builder, line1);
        ins2.model(builder, line2);
        ins3.model(builder, line3);
        ins4.model(builder, line4);
        ins5.model(builder, line5);

        root.addAll(line1, line2, line3, line4, line5);

        final JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(final Graphics g1) {
                super.paintComponent(g1);
                final Graphics2D g = (Graphics2D) g1;

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                final View<? extends Element> view = document.getView();
                view.paint(g);
            }
        };

        final View<?> view = root.getView();
        final Rectangle size = view.layout(0, 0);
        panel.setPreferredSize(new Dimension(size.width, size.height));

        frame.add(panel);

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
