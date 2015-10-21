package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.configuration.OS;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.SettingsManager;
import org.obicere.bcviewer.gui.swing.editor.SwingEditorPanel;
import org.obicere.bcviewer.gui.swing.menu.MainMenuBar;
import org.obicere.bcviewer.gui.swing.settings.SwingSettingsManager;

import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class SwingManager implements FrameManager {

    private final JFrame frame;

    private final SettingsManager<JComponent> settings;

    private final JPanel content;

    private final CardLayout contentLayout;

    private final JTabbedPane tabbedPane;

    private final String tabbedPaneName = "Tabbed";

    private final JPanel dropPane;

    private final String dropPaneName = "Drop";

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

        this.contentLayout = new CardLayout();
        this.content = new JPanel(contentLayout);
        this.tabbedPane = new JTabbedPane();
        this.dropPane = new JPanel();
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

        dropPane.setLayout(new BorderLayout());
        final JLabel icon = new JLabel(domain.getIcons().getIcon(Icons.ICON_DARK_256));
        icon.setBorder(new EmptyBorder(128, 128, 128, 128));

        dropPane.add(icon);

        content.add(tabbedPaneName, tabbedPane);
        content.add(dropPaneName, dropPane);

        contentLayout.show(content, dropPaneName);

        frame.setJMenuBar(menuBar);
        frame.add(content);

        frame.setDropTarget(getDropTarget());
        frame.setIconImages(Arrays.asList(domain.getIcons().getDarkApplicationImages()));
    }

    private DropTarget getDropTarget() {
        switch (OS.getOS()) {
            case WINDOWS:
                return new DropTarget() {
                    public synchronized void drop(final DropTargetDropEvent event) {
                        try {
                            event.acceptDrop(DnDConstants.ACTION_COPY);

                            final List<File> droppedFiles = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            final File[] files = droppedFiles.toArray(new File[droppedFiles.size()]);

                            domain.getClassLoader().load(files);
                        } catch (final Exception e) {
                            domain.getLogger().log(Level.WARNING, e.getMessage(), e);
                        }
                    }
                };
            default:
                return null;
        }
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
    public EditorPanel[] getEditorPanels() {
        final ArrayList<EditorPanel> panels = new ArrayList<>(tabbedPane.getComponentCount());
        for (final Component component : tabbedPane.getComponents()) {
            if (component instanceof EditorPanel) {
                panels.add((EditorPanel) component);
            }
        }
        return panels.toArray(new EditorPanel[panels.size()]);
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
        final int index = tabbedPane.getTabCount();

        final SwingEditorPanel panel = new SwingEditorPanel(domain);

        panel.setName(className);

        if (tabbedPane.getTabCount() == 0) {
            contentLayout.show(content, tabbedPaneName);
        }

        tabbedPane.addTab(className, panel);

        final JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));

        tabPanel.setOpaque(false);
        tabPanel.add(new JLabel(className));
        tabPanel.add(new TabCloseButton(className));

        tabbedPane.setTabComponentAt(index, tabPanel);

        return panel;
    }

    @Override
    public EditorPanel removeEditorPanel(final String className) {
        final SwingEditorPanel component = (SwingEditorPanel) getEditorPanel(className);
        if (component != null) {
            final int index = tabbedPane.indexOfTab(className);
            tabbedPane.removeTabAt(index);

            if (tabbedPane.getTabCount() == 0) {
                contentLayout.show(content, dropPaneName);
            }
            return component;
        }
        return null;
    }

    @Override
    public EditorPanel getOpenEditorPanel() {
        return (EditorPanel) tabbedPane.getSelectedComponent();
    }

    @Override
    public File[] promptForFiles(final String... extensions) {

        final String description = String.join(", ", extensions);
        final List<String> extensionList = Arrays.asList(extensions);

        final JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(final File f) {
                if (f.isDirectory()) {
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
                return extensionList.contains(extension);
            }

            @Override
            public String getDescription() {
                return description;
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

    private class TabCloseButton extends JButton {

        public TabCloseButton(final String className) {
            setPreferredSize(new Dimension(7, 7));
            setFocusable(false);
            setBorderPainted(false);
            setOpaque(false);
            setContentAreaFilled(false);
            addActionListener(e -> removeEditorPanel(className));
        }

        @Override
        protected void paintComponent(final Graphics g) {

            final ButtonModel model = getModel();
            if (model.isRollover()) {
                g.setColor(Color.DARK_GRAY);
            } else {

                g.setColor(Color.GRAY);
            }
            if (model.isPressed()) {
                g.translate(1, 1);
            }
            final Graphics2D g2 = (Graphics2D) g;

            final BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

            final int length = 3;
            final int mid = getWidth() / 2;
            g2.setStroke(stroke);
            g2.drawLine(mid - length, mid - length, mid + length, mid + length);
            g2.drawLine(mid - length, mid + length, mid + length, mid - length);
        }
    }
}
