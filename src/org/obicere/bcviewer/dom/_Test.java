package org.obicere.bcviewer.dom;

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

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final Element root = new Element("root");
        final Element page = new Element("page");
        for(int i = 1; i <= 3; i++){
            final TextElement element = new TextElement("elem" + i , "Element " + i);
            final TextAttributes attributes = element.getAttributes();
            attributes.setColor(Color.RED);
            attributes.setFont(new Font("Consolas", Font.PLAIN, 20));
            page.add(element);
        }
        page.setAxis(Element.AXIS_PAGE);
        final Element line = new Element("line");
        for(int i = 1; i <= 3; i++){
            final TextElement element = new TextElement("elem" + i , "Element " + i);
            final TextAttributes attributes = element.getAttributes();
            attributes.setColor(Color.ORANGE);
            attributes.setFont(new Font("Consolas", Font.PLAIN, 20));
            line.add(element);
        }
        line.setAxis(Element.AXIS_LINE);

        root.add(page);
        root.add(line);

        final JFrame frame = new JFrame("Text view test");
        final JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(final Graphics g1) {
                final Graphics2D g = (Graphics2D) g1;

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                final View<?> view = root.getView();
                view.layout(0, 0);
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
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

}
