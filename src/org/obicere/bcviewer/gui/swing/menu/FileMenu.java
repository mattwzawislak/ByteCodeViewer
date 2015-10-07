package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.SettingsManager;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 */
public class FileMenu extends JMenu {

    public FileMenu(final Domain domain) {
        super("File");
        setName("file");
        setMnemonic('F');

        final JMenuItem open = new JMenuItem("Open");
        final JMenuItem settings = new JMenuItem("Settings");
        final JMenuItem exit = new JMenuItem("Exit");

        open.setName("open");
        settings.setName("settings");
        exit.setName("exit");

        open.setMnemonic('O');
        settings.setMnemonic('t');
        exit.setMnemonic('x');

        open.addActionListener(e -> SwingUtilities.invokeLater(() -> domain.getClassLoader().promptAndLoad()));
        settings.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            final SettingsManager<?> manager = domain.getGUIManager().getFrameManager().getSettingsManager();
            manager.setVisible(!manager.isVisible());
        }));
        exit.addActionListener(e -> domain.getGUIManager().getFrameManager().close());

        add(open);
        add(settings);
        addSeparator();
        add(exit);
    }
}
