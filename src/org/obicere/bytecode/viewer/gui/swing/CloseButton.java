package org.obicere.bytecode.viewer.gui.swing;

import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.context.Domain;

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
