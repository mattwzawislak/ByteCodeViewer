package org.obicere.bcviewer.gui.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        setName("menubar");

        add(createFile());
        add(createView());
    }

    private JMenu createFile() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setName("file");
        fileMenu.setMnemonic('F');

        final JMenuItem open = new JMenuItem("Open");
        final JMenuItem exit = new JMenuItem("Exit");

        open.setName("open");
        exit.setName("exit");

        open.setMnemonic('O');
        exit.setMnemonic('x');

        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createView(){
        final JMenu viewMenu = new JMenu("View");
        viewMenu.setName("view");
        viewMenu.setMnemonic('V');

        return viewMenu;
    }

}
