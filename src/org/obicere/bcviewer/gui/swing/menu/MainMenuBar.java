package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;

import javax.swing.JMenuBar;

/**
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar(final Domain domain) {

        add(new FileMenu(domain));
        add(new ViewMenu(domain));
    }
}
