package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Image;

/**
 */
public class IconButton extends JButton {

    private final Domain domain;

    private final String plain;
    private final String hover;

    public IconButton(final Domain domain, final String plain, final String hover) {

        this.domain = domain;
        this.plain = plain;
        this.hover = hover;

        setFocusable(false);
        setBorderPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Icons icons = domain.getIcons();

        final String imageName;
        final ButtonModel model = getModel();
        if (model.isRollover()) {
            imageName = hover;
        } else {
            imageName = plain;
        }
        final Image image = icons.getImage(imageName);
        if (model.isPressed()) {
            g.translate(1, 1);
        }
        g.drawImage(image, 0, 0, getWidth() - 1, getHeight() - 1, this);
    }
}
