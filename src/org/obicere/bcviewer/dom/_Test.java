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

        final Document document = new Document(() -> frame.getContentPane().getBounds());

        final Element root = document.getRoot();

        final CollapsibleElement element = new CollapsibleElement("view");

        final TextElement collapseMe = new TextElement("collapse", "Click to collapse this");
        collapseMe.setAttributes(attributes);
        element.add(collapseMe);

        root.add(element);
        for (int i = 0; i < 8; i++) {
            final TextElement parent = new TextElement("parent" + i, "Parent #" + i);
            parent.setAttributes(attributes);
            parent.setAxis((i % 2 == 0) ? Element.AXIS_LINE : Element.AXIS_PAGE);
            for (int j = 0; j < 4; j++) {

                final TextElement child = new TextElement("child" + j, "Element #(" + i + ", " + j + ")");

                child.setLeftPad(4);
                child.setAttributes(attributes);
                parent.add(child);
            }
            root.add(parent);
        }

        final AtomicReference<Caret> caret = new AtomicReference<>();

        final JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(final Graphics g1) {
                super.paintComponent(g1);
                final Graphics2D g = (Graphics2D) g1;

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                final View<? extends Element> view = document.getView();
                view.paint(g);
                if (caret.get() != null) {
                    final Caret bounds = caret.get();
                    final TextView textView = (TextView) bounds.getElement().getView();
                    final int x = textView.getCaretLocation(bounds.getIndex());
                    final Rectangle size = textView.getSize();
                    g.fillRect(size.x + x, size.y, 2, size.height);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                element.setCollapsed(!element.isCollapsed());
                document.invalidate();
                frame.repaint();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                move(e);
            }

            @Override
            public void mouseMoved(final MouseEvent e) {
                move(e);
            }

            @Override
            public void mouseDragged(final MouseEvent e) {
                move(e);
            }

            private void move(final MouseEvent e) {
                final Element element = document.getElementAt(e.getX(), e.getY());
                if (element instanceof TextElement) {
                    caret.set(((TextElement) element).getCaret(e.getX(), e.getY()));
                } else {
                    caret.set(null);
                }
                frame.repaint();
            }
        });

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
