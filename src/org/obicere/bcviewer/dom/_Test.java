package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {

        final JFrame frame = new JFrame("Text view test");

        final AtomicReference<Rectangle> hover = new AtomicReference<>();

        final JDocumentArea area = new JDocumentArea() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                System.out.println(hover.get());
                if (hover.get() != null) {
                    final Rectangle get = hover.get();
                    g.setColor(Color.RED);
                    g.drawRect(get.x, get.y, get.width, get.height);
                }
            }
        };

        final DocumentBuilder builder = new DocumentBuilder();
        final Document document = new Document(area);

        final Element root = document.getRoot();

        area.setDocument(document);

        Element last = root;
        for (int i = 0; i < 30; i++) {
            final TextElement next = new TextElement("test" + i, "test" + i);
            next.setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_STRING));

            last.add(next);
            last = next;
        }

        area.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                final Element get = document.getElementAt(e.getX(), e.getY());

                if (get == null) {
                    hover.set(null);
                } else {
                    hover.set(get.getView().getBounds());
                }
                frame.repaint();
            }
        });

        frame.add(new JScrollPane(area));

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }
}
