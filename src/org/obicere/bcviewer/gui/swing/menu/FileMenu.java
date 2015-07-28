package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 */
public class FileMenu extends JMenu {

    public FileMenu(final Domain domain) {
        super("File");
        setName("file");
        setMnemonic('F');

        final JMenuItem open = new JMenuItem("Open");
        final JMenuItem exit = new JMenuItem("Exit");

        open.setName("open");
        exit.setName("exit");

        open.setMnemonic('O');
        exit.setMnemonic('x');

        exit.addActionListener(e -> domain.getGUIManager().getFrameManager().close());

        add(open);
        addSeparator();
        add(exit);
    }
}
