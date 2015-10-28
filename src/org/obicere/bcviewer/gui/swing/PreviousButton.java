package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;

import java.awt.Dimension;

/**
 */
public class PreviousButton extends IconButton {

    public PreviousButton(final Domain domain) {
        super(domain, Icons.ICON_BACKWARD_NAV, Icons.ICON_BACKWARD_NAV);

        final Dimension dimension = new Dimension(17, 17);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
    }
}
