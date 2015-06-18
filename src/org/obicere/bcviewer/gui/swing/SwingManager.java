package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.gui.FrameManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

        for (int i = 0; i < paths.length; i++) {
            if (paths[i].length() == 0) {
                return null;
            }
            if (current instanceof Container) {
                final Component next = childWithName((Container) current, paths[i]);

                // Didn't find element in path, return null
                if (next == null) {
                    return null;
                } else {
                    if (i == paths.length - 1) {
                        return next;
                    }
                    current = next;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private Component childWithName(final Container container, final String name) {
        final Component[] children = container.getComponents();
        if (children.length != 0) {
            for (final Component child : children) {
                final String childName = child.getName();
                if (childName != null && childName.equals(name)) {
                    return child;
                }
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
        final JPanel panel = new JPanel();
        panel.setName("container");
        final JButton button = new JButton();
        button.setName("button");

        panel.add(button);
        frame.add(panel);
    }

}
