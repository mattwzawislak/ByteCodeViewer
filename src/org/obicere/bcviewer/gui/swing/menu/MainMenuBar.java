package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;

import javax.swing.JMenuBar;

/**
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar(final Domain domain) {

        setName("menubar");

        add(new FileMenu(domain));
        add(new ViewMenu(domain));
        add(new ThemeMenu(domain));
    }
}
