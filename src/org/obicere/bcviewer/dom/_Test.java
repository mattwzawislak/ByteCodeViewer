package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.Constant;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.ConstantUtf8;
import org.obicere.bcviewer.bytecode.instruction.new_;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final DocumentBuilder builder = new DocumentBuilder();
        builder.constantPool = new ConstantPool(new Constant[]{null, new ConstantUtf8("55555"), new ConstantUtf8("666666"), new ConstantUtf8("7777777"), new ConstantUtf8("88888888")});
        final Document document = new Document(() -> frame.getContentPane().getBounds());

        final Element root = document.getRoot();

        final new_ ins1 = new new_(0, 1);
        final new_ ins2 = new new_(0, 2);
        final new_ ins3 = new new_(0, 3);
        final new_ ins4 = new new_(0, 4);

        final BasicElement line1 = new BasicElement("line1");
        final BasicElement line2 = new BasicElement("line2");
        final BasicElement line3 = new BasicElement("line3");
        final BasicElement line4 = new BasicElement("line4");

        line1.setAxis(Element.AXIS_LINE);
        line2.setAxis(Element.AXIS_LINE);
        line3.setAxis(Element.AXIS_LINE);
        line4.setAxis(Element.AXIS_LINE);

        ins1.model(builder, line1);
        ins2.model(builder, line2);
        ins3.model(builder, line3);
        ins4.model(builder, line4);

        root.addAll(line1, line2, line3, line4);

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
