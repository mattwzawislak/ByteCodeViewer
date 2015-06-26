package org.obicere.bcviewer.gui.swing;

import com.alee.laf.WebLookAndFeel;
import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.swing.menu.MainMenuBar;
import org.obicere.bcviewer.gui.swing.tree.AnnotationPublicBytecodeNode;
import org.obicere.bcviewer.gui.swing.tree.BytecodeTree;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class SwingManager implements FrameManager {

    private final JFrame frame;

    private final LookAndFeel defaultLookAndFeel;

    private final Domain domain;

    public SwingManager(final Domain domain) {
        this.domain = domain;
        this.frame = new JFrame(domain.getApplicationName());

        this.defaultLookAndFeel = new WebLookAndFeel();

        // Install the default look and feel. This is if the default LAF is
        // not a pre-installed LAF.
        UIManager.installLookAndFeel(defaultLookAndFeel.getName(), defaultLookAndFeel.getClass().getName());
    }

    @Override
    public Object getWindow() {
        return frame;
    }

    @Override
    public Object getComponent(final String name) {
        if (name == null || name.length() == 0) {
            return null;
        }

        // Name should be of form: 'foo.bar.tee'
        final String[] paths = name.split("\\.");

        Component current = frame.getContentPane();
        int i = 0;

        // Check to see if this is the menu bar. This needs to be handled
        // separately, since the menu bar is not a child of the content pane
        if (paths[0].equals("menubar")) {
            current = frame.getJMenuBar();
            // Increment i, as we already advanced along the path
            // by checking equality with the menu bar
            i++;
        }

        for (; i < paths.length; i++) {
            if (paths[i].length() == 0) {
                return null;
            }
            Component next = null;
            if (current instanceof JMenuBar) {
                next = childWithName((JMenuBar) current, paths[i]);
            } else if (current instanceof Container) {
                next = childWithName((Container) current, paths[i]);
            }
            if (next == null) {
                return null;
            } else {
                if (i == paths.length - 1) {
                    return next;
                }
                current = next;
            }
        }
        return null;
    }

    private Component childWithName(final JMenuBar bar, final String name) {
        final MenuElement[] elements = bar.getSubElements();
        for (final MenuElement element : elements) {
            final Component next = element.getComponent();
            if (next != null && next.getName().equals(name)) {
                return next;
            }
        }
        return null;
    }

    private Component childWithName(final Container container, final String name) {
        final Component[] children = container.getComponents();
        for (final Component child : children) {
            final String childName = child.getName();
            if (childName != null && childName.equals(name)) {
                return child;
            }
        }
        return null;
    }

    @Override
    public void open() {
        if (frame.isVisible()) {
            return;
        }
        addComponents();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void addComponents() {
        final MainMenuBar menuBar = new MainMenuBar(domain);

        final JPanel content = new JPanel();

        final BytecodeTree tree = new BytecodeTree(null);
        final JScrollPane scrollPane = new JScrollPane(tree);

        final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        final DefaultMutableTreeNode node = new DefaultMutableTreeNode("Root");

        final Icons domain = this.domain.getIcons();

        final DefaultMutableTreeNode c1 = new AnnotationPublicBytecodeNode(domain);
        final DefaultMutableTreeNode c2 = new AnnotationPublicBytecodeNode(domain);

        c1.add(new AnnotationPublicBytecodeNode(domain));
        c1.add(new AnnotationPublicBytecodeNode(domain));
        c2.add(new AnnotationPublicBytecodeNode(domain));
        c2.add(new AnnotationPublicBytecodeNode(domain));
        node.add(c1);
        node.add(c2);

        model.setRoot(node);

        content.add(scrollPane, BorderLayout.WEST);

        frame.setJMenuBar(menuBar);
        frame.setContentPane(content);
    }

    @Override
    public void close() {
        if (!frame.isVisible()) {
            return;
        }
        frame.dispose();
    }

    @Override
    public void requestFocus() {
        frame.requestFocus();
    }

    @Override
    public String getTitle() {
        return frame.getTitle();
    }

    @Override
    public void setTitle(final String name) {
        frame.setTitle(name);
    }

    @Override
    public void setSize(final int width, final int height) {
        frame.setSize(width, height);
    }

    @Override
    public void paint() {
        frame.repaint();
    }

    @Override
    public void validate() {
        frame.revalidate();
    }

    @Override
    public void pack() {
        frame.pack();
    }

    @Override
    public String getDefaultThemeName(){
        return defaultLookAndFeel.getName();
    }

    @Override
    public String[] getAvailableThemeNames(){
        final UIManager.LookAndFeelInfo themes[] = UIManager.getInstalledLookAndFeels();

        final int length = themes.length;
        final String[] names = new String[length];
        for (int i = 0; i < length; i++) {
            names[i] = themes[i].getName();
        }
        return names;
    }

    @Override
    public void loadDefaultTheme() {
        try {
            UIManager.setLookAndFeel(defaultLookAndFeel);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        } catch (final UnsupportedLookAndFeelException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void loadTheme(final String name){
        try {
            final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
            for (final UIManager.LookAndFeelInfo theme : themes) {
                if (theme.getName().equals(name)) {
                    UIManager.setLookAndFeel(theme.getClassName());
                }
            }
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        } catch (final UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
