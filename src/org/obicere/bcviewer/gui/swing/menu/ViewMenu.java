package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.EditorPanel;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 */
public class ViewMenu extends JMenu {

    public ViewMenu(final Domain domain) {
        super("View");
        setMnemonic('V');

        final JMenuItem reload = new JMenuItem("Reload");
        final JMenuItem hardReload = new JMenuItem("Hard Reload");

        reload.addActionListener(e -> {
            final EditorPanel panel = domain.getGUIManager().getFrameManager().getOpenEditorPanel();

            if (panel != null) {
                panel.reload();
            }
        });
        hardReload.addActionListener(e -> {
            final EditorPanel panel = domain.getGUIManager().getFrameManager().getOpenEditorPanel();
            if (panel != null) {
                panel.hardReload();
            }
        });

        add(reload);
        add(hardReload);
    }
}
