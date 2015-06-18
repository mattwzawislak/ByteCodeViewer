package org.obicere.bcviewer;

import org.obicere.bcviewer.gui.swing.SwingManager;

import javax.swing.SwingUtilities;

/**
 * @author Obicere
 */
public class Boot {


    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();
        final SwingManager manager = new SwingManager("Test");
        SwingUtilities.invokeLater(manager::open);

    }

}
