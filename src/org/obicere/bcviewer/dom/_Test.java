package org.obicere.bcviewer.dom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public class _Test {

    public _Test() {
        final TextElement element = new TextElement("test", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        final TextAttributes attributes = element.getAttributes();

        attributes.setColor(new Resource<>(Color.BLACK));
        attributes.setFont(new Resource<>(new Font("Courier", Font.PLAIN, 20)));

        final JFrame frame = new JFrame("Text view test");
        final JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(final Graphics g) {
                final Rectangle bounds = g.getFontMetrics(attributes.getFont().get()).getStringBounds(element.getText(), g).getBounds();

                attributes.setScript(Script.BASELINE);
                bounds.x = 10;
                bounds.y += 50;
                element.getView().paint(g, bounds);

                attributes.setScript(Script.SUBSCRIPT);
                bounds.x += bounds.width;
                element.getView().paint(g, bounds);

                attributes.setScript(Script.SUPERSCRIPT);
                element.getView().paint(g, bounds);
            }
        };

        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

}
