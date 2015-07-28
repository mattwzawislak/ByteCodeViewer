package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.swing.editor.EditorPanel;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

/**
 */
public class ViewMenu extends JMenu {

    public ViewMenu(final Domain domain){
        super("View");
        setName("view");
        setMnemonic('V');

        final JCheckBoxMenuItem showEditorBytes = new JCheckBoxMenuItem("Show Editor Bytes", true);

        showEditorBytes.addActionListener(e -> {
            final boolean show = showEditorBytes.isSelected();
            final Object component = domain.getGUIManager().getFrameManager().getComponent("content.editor");
            if (component != null) {
                EditorPanel c = (EditorPanel) component;
                c.setBytesPanelVisible(show);
            }
            //domain.getGUIManager().getFrameManager().paint();
        });

        add(showEditorBytes);
    }
}
