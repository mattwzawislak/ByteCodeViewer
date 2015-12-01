package org.obicere.bytecode.viewer.gui.swing.menu;

import org.obicere.bytecode.viewer.context.Domain;

import javax.swing.JMenuBar;

/**
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar(final Domain domain) {

        add(new FileMenu(domain));
        add(new ViewMenu(domain));
    }
}
