package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;

import java.awt.Dimension;

/**
 */
public class NextButton extends IconButton {

    public NextButton(final Domain domain) {
        super(domain, Icons.ICON_FORWARD_NAV, Icons.ICON_FORWARD_NAV);

        final Dimension dimension = new Dimension(17, 17);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
    }
}
