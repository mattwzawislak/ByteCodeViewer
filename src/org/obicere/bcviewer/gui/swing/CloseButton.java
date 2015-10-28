package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 */

public class CloseButton extends JButton {

    private final Domain domain;

    public CloseButton(final Domain domain) {
        this.domain = domain;

        setPreferredSize(new Dimension(9, 9));
        setFocusable(false);
        setBorderPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Icons icons = domain.getIcons();

        final Image image;
        final ButtonModel model = getModel();
        if (model.isRollover()) {
            image = icons.getImage(Icons.ICON_CLOSE_HOVER);
        } else {
            image = icons.getImage(Icons.ICON_CLOSE);
        }
        if (model.isPressed()) {
            g.translate(1, 1);
        }
        g.drawImage(image, 0, 0, 8, 8, this);
    }
}
