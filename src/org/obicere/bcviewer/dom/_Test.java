package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.ui.DocumentRenderer;

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

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final Document document = new Document(() -> frame.getContentPane().getBounds());

        final Element root = document.getRoot();
        final Element page = new BasicElement("page");

        root.add(page);
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

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final TextElement element = new TextElement("elem" + e.getWhen(), "Element #" + page.getChildrenCount());
                final TextAttributes attributes = element.getAttributes();
                attributes.setFont(new Font("Courier new", Font.PLAIN, 20));
                page.add(element);

                document.invalidate();
                panel.repaint();
            }
        });

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
