package org.obicere.bcviewer.gui.swing;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.EditorPanelManager;
import org.obicere.bcviewer.gui.swing.editor.SwingEditorPanel;
import org.obicere.bcviewer.gui.swing.tree.BytecodeTree;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Obicere
 */
public class SwingEditorPanelManager implements EditorPanelManager {

    private final BytecodeTree tree;

    private final JPanel editorArea;

    private final CardLayout contentLayout;

    private final JTabbedPane tabbedPane;

    private final String tabbedPaneName = "Tabbed";

    private final JPanel dropPane;

    private final String dropPaneName = "Drop";

    private final Domain domain;

    private final HashMap<String, SwingEditorPanel> editorPanels = new HashMap<>();

    public SwingEditorPanelManager(final Domain domain) {

        this.domain = domain;

        this.tree = new BytecodeTree(domain);
        this.contentLayout = new CardLayout();
        this.editorArea = new JPanel(contentLayout);
        this.tabbedPane = new JTabbedPane();
        this.dropPane = new JPanel();
    }

    public JComponent getContent() {

        final JPanel content = new JPanel(new BorderLayout());

        final JLabel icon = new JLabel(domain.getIcons().getIcon(Icons.ICON_DARK_256));
        icon.setBorder(new EmptyBorder(128, 128, 128, 128));

        dropPane.setLayout(new BorderLayout());
        dropPane.add(icon);

        final JSplitPane displayArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        final JScrollPane treeScrollPane = new JScrollPane(tree);

        displayArea.setRightComponent(tabbedPane);
        displayArea.setLeftComponent(treeScrollPane);

        editorArea.add(tabbedPaneName, displayArea);
        editorArea.add(dropPaneName, dropPane);

        contentLayout.show(editorArea, dropPaneName);

        content.add(editorArea);

        return content;
    }

    @Override
    public EditorPanel getEditorPanel(final String className) {
        for (final Component component : tabbedPane.getComponents()) {
            if (component instanceof EditorPanel) {
                if (component.getName().equals(className)) {
                    return (EditorPanel) component;
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
    public EditorPanel displayEditorPanel(final String className) {
        final SwingEditorPanel panel = (SwingEditorPanel) getEditorPanel(className);
        if (panel != null) {
            tabbedPane.setSelectedComponent(panel);
            return panel;
        }
        final SwingEditorPanel cached = editorPanels.get(className);
        if (cached != null) {
            display(cached, className);
        }
        return null;
    }

    @Override
    public EditorPanel addEditorPanel(final EditorPanel panel, final String className) {
        final String qualifiedName = BytecodeUtils.getQualifiedName(className);

        editorPanels.put(qualifiedName, (SwingEditorPanel) panel);
        tree.addClass(panel.getClassFile());

        if (tabbedPane.getTabCount() == 0) {
            contentLayout.show(editorArea, tabbedPaneName);
        }

        return panel;
    }

    private void display(final SwingEditorPanel panel, final String className) {
        SwingUtilities.invokeLater(() -> {
            if (panel == null || className == null) {
                return;
            }
            int index = tabbedPane.indexOfComponent(panel);
            if (index < 0) {

                index = tabbedPane.getTabCount();

                panel.setName(className);

                tabbedPane.addTab(className, panel);

                final JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
                final String shortName = BytecodeUtils.getClassName(className);

                tabPanel.setOpaque(false);
                tabPanel.add(new JLabel(shortName));
                tabPanel.add(new TabCloseButton(className));

                tabbedPane.setTabComponentAt(index, tabPanel);
            }
            tabbedPane.setSelectedIndex(index);
        });
    }

    @Override
    public EditorPanel createEditorPanel() {
        return new SwingEditorPanel(domain);
    }

    @Override
    public EditorPanel closeEditorPanel(final String className) {
        final SwingEditorPanel component = (SwingEditorPanel) getEditorPanel(className);
        if (component != null) {
            final int index = tabbedPane.indexOfTab(className);
            tabbedPane.removeTabAt(index);

            if (editorPanels.size() == 0) {
                contentLayout.show(editorArea, dropPaneName);
            }
            return component;
        }
        return null;
    }

    @Override
    public EditorPanel getOpenEditorPanel() {
        return (EditorPanel) tabbedPane.getSelectedComponent();
    }

    private class TabCloseButton extends JButton {

        public TabCloseButton(final String className) {
            setPreferredSize(new Dimension(9, 9));
            setFocusable(false);
            setBorderPainted(false);
            setOpaque(false);
            setContentAreaFilled(false);
            addActionListener(e -> closeEditorPanel(className));
        }

        @Override
        protected void paintComponent(final Graphics g) {
            final Icons icons = domain.getIcons();

            final Image image;
            final ButtonModel model = getModel();
            if (model.isRollover()) {
                image = icons.getImage(Icons.ICON_CLOSE_HOVER);
            } else {
                image = icons.getImage(Icons.ICON_CLOSE);
            }
            if (model.isPressed()) {
                g.translate(1, 1);
            }
            g.drawImage(image, 0, 0, 8, 8, this);
        }
    }
}
