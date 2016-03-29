package org.obicere.bytecode.viewer.gui.swing;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.meta.MetaClassFile;
import org.obicere.bytecode.viewer.concurrent.ClassLoaderService;
import org.obicere.bytecode.viewer.concurrent.ClassModelerService;
import org.obicere.bytecode.viewer.concurrent.RequestCallback;
import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.EditorPanel;
import org.obicere.bytecode.viewer.gui.EditorPanelManager;
import org.obicere.bytecode.viewer.gui.swing.editor.SwingEditorPanel;
import org.obicere.bytecode.viewer.gui.swing.tree.ByteCodeTree;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

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
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Obicere
 */
public class SwingEditorPanelManager implements EditorPanelManager {

    private final ByteCodeTree tree;

    private final JPanel editorArea;

    private final CardLayout contentLayout;

    private final JTabbedPane tabbedPane;

    private final String tabbedPaneName = "Tabbed";

    private final JPanel dropPane;

    private final String dropPaneName = "Drop";

    private final Domain domain;

    private final Map<String, SwingEditorPanel> editorPanels = new HashMap<>();

    public SwingEditorPanelManager(final Domain domain) {

        this.domain = domain;

        this.tree = new ByteCodeTree(domain);
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

        displayArea.setDividerLocation(128);
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
        return editorPanels.get(className);
    }

    @Override
    public EditorPanel[] getEditorPanels() {
        final Collection<? extends EditorPanel> panels = editorPanels.values();
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
            if (tabbedPane.indexOfComponent(panel) >= 0) {
                tabbedPane.setSelectedComponent(panel);
            } else {
                display(panel, className);
            }
            return panel;
        }

        final ClassInformation classInformation = domain.getClassStorage().getClass(className);

        if (classInformation != null) {
            final ClassFile file = classInformation.getClassFile();
            final SwingEditorPanel editorPanel = new SwingEditorPanel(domain);

            editorPanels.put(className, editorPanel);

            final ClassModelerService modeler = domain.getClassModelerService();
            if (file.isMeta()) {
                final ClassLoaderService loader = domain.getClassLoaderService();

                loader.postRequest(new RequestCallback() {
                    @Override
                    public void notifyCompletion() {
                        // this classInformation will reference the non-meta
                        // and newly loaded information
                        final ClassInformation classInformation = domain.getClassStorage().getClass(className);
                        modeler.postRequest(new RequestCallback(), editorPanel.getBuilder(), classInformation);
                    }
                }, classInformation.getSource());
            } else {
                modeler.postRequest(new RequestCallback(), editorPanel.getBuilder(), classInformation);
            }
            return editorPanel;
        }
        return null;
    }

    @Override
    public void addClass(final ClassInformation classInformation) {
        final ClassFile rootClass = classInformation.getClassFile();
        SwingUtilities.invokeLater(() -> {
            tree.addClass(rootClass);
        });

        if(tabbedPane.getTabCount() == 0){
            contentLayout.show(editorArea, tabbedPaneName);
        }
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
                final String shortName = ByteCodeUtils.getClassName(className);
                final CloseButton button = new CloseButton(domain);
                button.addActionListener(e -> closeEditorPanel(className));

                tabPanel.setOpaque(false);
                tabPanel.add(new JLabel(shortName));
                tabPanel.add(button);

                tabbedPane.setTabComponentAt(index, tabPanel);
            }
            tabbedPane.setSelectedIndex(index);
        });
    }


    @Override
    public EditorPanel removeEditorPanel(final String className) {
        closeEditorPanel(className);
        tree.removeClass(className);
        final EditorPanel panel = editorPanels.remove(className);

        if (editorPanels.size() == 0) {
            contentLayout.show(editorArea, dropPaneName);
        }

        return panel;
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
}
