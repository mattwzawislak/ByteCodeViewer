package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.context.Domain;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 */
public class DropTargetPanel extends JPanel {

    public DropTargetPanel(final Domain domain) {
        add(new JLabel("Drop files"));
    }

}
