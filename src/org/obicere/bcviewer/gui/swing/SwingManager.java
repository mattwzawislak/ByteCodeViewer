package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.SettingsManager;
import org.obicere.bcviewer.gui.swing.editor.SwingEditorPanel;
import org.obicere.bcviewer.gui.swing.menu.MainMenuBar;
import org.obicere.bcviewer.gui.swing.settings.SwingSettingsManager;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Obicere
 */
public class SwingManager implements FrameManager {

    private final JFrame frame;

    private final SettingsManager<JComponent> settings;

    private final JTabbedPane tabbedPane;

    private final Domain domain;

    public SwingManager(final Domain domain) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.domain = domain;
        this.frame = new JFrame(domain.getApplicationName());
        this.settings = new SwingSettingsManager(frame, domain);
        this.tabbedPane = new JTabbedPane();
    }

    @Override
    public void open() {
        if (frame.isVisible()) {
            return;
        }
        addComponents();
        settings.initialize();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void addComponents() {
        final MainMenuBar menuBar = new MainMenuBar(domain);
        final JPanel content = new JPanel(new BorderLayout());

        tabbedPane.setName("tabs");

        content.setName("content");
        content.add(tabbedPane);

        frame.setJMenuBar(menuBar);
        frame.add(content);
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
    public EditorPanel getEditorPanel(final String className) {
        for (final Component component : tabbedPane.getComponents()) {
            if (component instanceof EditorPanel) {
                final SwingEditorPanel panel = (SwingEditorPanel) component;
                if (panel.getName().equals(className)) {
                    return panel;
                }
            }
        }
        return null;
    }

    @Override
    public boolean hasEditorPanel(final String className) {
        return getEditorPanel(className) != null;
    }

    @Override
    public void displayEditorPanel(final String className) {
        final EditorPanel panel = getEditorPanel(className);
        if (panel != null) {
            tabbedPane.setSelectedComponent((Component) panel);
        }
    }

    @Override
    public EditorPanel createEditorPanel(final String className) {
        final SwingEditorPanel panel = new SwingEditorPanel(domain);
        panel.setName(className);
        tabbedPane.add(panel);
        return panel;
    }

    @Override
    public File[] promptForFiles(final String... extensions) {

        final List<String> listExtensions = Arrays.asList(extensions);
        final String description = listExtensions.toString();
        final String trimmedDescription = description.substring(1, description.length() - 1);

        final JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(final File f) {
                if(f.isDirectory()){
                    return true;
                }
                final String name = f.getName();
                final int lastIndex = name.lastIndexOf('.');
                final String extension;
                if (lastIndex > 0) {
                    extension = name.substring(lastIndex);
                } else {
                    return false;
                }
                return listExtensions.contains(extension);
            }

            @Override
            public String getDescription() {
                return trimmedDescription;
            }
        });

        final int response = chooser.showOpenDialog(frame);

        if (response == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFiles();
        } else {
            return null;
        }
    }

    @Override
    public SettingsManager<?> getSettingsManager() {
        return settings;
    }
}
