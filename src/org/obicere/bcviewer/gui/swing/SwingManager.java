package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.swing.menu.MainMenuBar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.MenuElement;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Container;

/**
 * @author Obicere
 */
public class SwingManager implements FrameManager {

    private JFrame frame;

    public SwingManager(final String title) {
        this.frame = new JFrame(title);
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
            // Incremenet i, as we already advanced along the path
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

    private void addComponents() {
        final MainMenuBar menuBar = new MainMenuBar();
        frame.setJMenuBar(menuBar);

        final JPanel panel = new JPanel();
        panel.setName("container");
        final JButton button = new JButton();
        button.setName("button");

        panel.add(button);
        frame.add(panel);
    }

}
