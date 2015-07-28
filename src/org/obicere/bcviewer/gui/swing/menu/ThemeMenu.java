package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

/**
 */
public class ThemeMenu extends JMenu {

    public ThemeMenu(final Domain domain) {
        super("Theme");
        setName("theme");
        setMnemonic('T');

        final FrameManager manager = domain.getGUIManager().getFrameManager();

        final ActionListener changeThemeListener = e -> manager.loadTheme(e.getActionCommand());

        for (final String theme : manager.getAvailableThemeNames()) {
            final JMenuItem item = new JMenuItem(theme);
            item.setName(theme);
            item.setActionCommand(theme);
            item.addActionListener(changeThemeListener);
            add(item);
        }
    }
}
