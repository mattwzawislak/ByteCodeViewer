package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;

import javax.swing.JMenu;

/**
 */
public class ViewMenu extends JMenu {

    public ViewMenu(final Domain domain){
        super("View");
        setMnemonic('V');
    }
}
