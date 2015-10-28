package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;

import java.awt.Dimension;

/**
 */

public class CloseButton extends IconButton {

    public CloseButton(final Domain domain) {
        super(domain, Icons.ICON_CLOSE, Icons.ICON_CLOSE_HOVER);

        final Dimension dimension = new Dimension(9, 9);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
    }

}
