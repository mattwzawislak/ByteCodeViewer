package org.obicere.bcviewer.gui.swing.menu;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 */
public class MainMenuBar extends JMenuBar {

    private final Domain domain;

    public MainMenuBar(final Domain domain) {
        this.domain = domain;

        setName("menubar");

        add(createFile());
        add(createView());
        add(createTheme());
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

        exit.addActionListener(e -> domain.getGUIManager().getFrameManager().close());

        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createView(){
        final JMenu viewMenu = new JMenu("View");
        viewMenu.setName("view");
        viewMenu.setMnemonic('V');

        final JCheckBoxMenuItem showEditorBytes = new JCheckBoxMenuItem("Show Editor Bytes", true);

        showEditorBytes.addActionListener(e -> {
            final boolean show = showEditorBytes.isSelected();
            final Object component = domain.getGUIManager().getFrameManager().getComponent("content.editor.split.bytesScroll");
            if(component != null){
                Component c = (Component) component;
                c.setVisible(show);
                c.getParent().revalidate();
                c.getParent().repaint();
            }
            domain.getGUIManager().getFrameManager().paint();
        });

        viewMenu.add(showEditorBytes);

        return viewMenu;
    }

    private JMenu createTheme(){
        final JMenu themeMenu = new JMenu("Theme");
        themeMenu.setName("theme");
        themeMenu.setMnemonic('T');

        final FrameManager manager = domain.getGUIManager().getFrameManager();

        final ActionListener changeThemeListener = e -> manager.loadTheme(e.getActionCommand());

        for (final String theme : manager.getAvailableThemeNames()){
            final JMenuItem item = new JMenuItem(theme);
            item.setName(theme);
            item.setActionCommand(theme);
            item.addActionListener(changeThemeListener);
            themeMenu.add(item);
        }

        return themeMenu;
    }

}
