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
        final TextElement element = new TextElement("test", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        final TextAttributes attributes = element.getAttributes();

        attributes.setColor(Color.BLACK);
        attributes.setFont(new Font("Courier", Font.PLAIN, 40));

        final JFrame frame = new JFrame("Text view test");
        final JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(final Graphics g1) {
                final Graphics2D g = (Graphics2D) g1;

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                final Rectangle bounds = g.getFontMetrics(attributes.getFont()).getStringBounds(element.getText(), g).getBounds();

                attributes.setScript(Script.BASELINE);
                attributes.setUnderline(true);
                attributes.setStrikeThrough(true);
                bounds.x = 10;
                bounds.y += 50;
                element.getView().paint(g, bounds);

                attributes.setScript(Script.SUBSCRIPT);
                attributes.setUnderline(true);
                attributes.setStrikeThrough(true);
                bounds.x += bounds.width;
                element.getView().paint(g, bounds);

                attributes.setScript(Script.SUPERSCRIPT);
                attributes.setUnderline(true);
                attributes.setStrikeThrough(true);
                element.getView().paint(g, bounds);
            }
        };

        panel.setPreferredSize(new Dimension(1200, 300));

        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

}
