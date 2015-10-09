package org.obicere.bcviewer.gui.swing.settings;

import com.alee.utils.laf.WeblafBorder;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.SettingsManager;
import org.obicere.bcviewer.gui.settings.SettingModelFactory;
import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.Group;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.target.ColorSetting;
import org.obicere.bcviewer.settings.target.Setting;
import org.obicere.utility.swing.VerticalFlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Set;

/**
 */
public class SwingSettingsManager implements DomainAccess, SettingsManager<JComponent> {

    private final SettingModelFactory<JComponent> factory = new SettingModelFactory<>();

    private final JDialog frame;

    private final Domain domain;

    public SwingSettingsManager(final Frame owner, final Domain domain) {
        this.domain = domain;

        this.frame = new JDialog(owner, "Settings");

        factory.addModeler(ColorSetting.class, new ColorSettingModeler(domain));
    }

    @Override
    public <T> void addModeler(final Class<? extends Setting<T>> cls, final SettingModeler<T, JComponent> modeler) {
        factory.addModeler(cls, modeler);
    }

    private void buildComponents() {
        final JPanel content = new JPanel(new BorderLayout(10, 10));

        final JPanel settings = buildSettings();
        final JPanel controls = buildControls();

        content.add(settings, BorderLayout.CENTER);
        content.add(controls, BorderLayout.SOUTH);

        frame.add(content);
        frame.pack();
    }

    private JPanel buildSettings() {
        final JPanel content = new JPanel(new BorderLayout(5, 5));

        final SettingsController controller = domain.getSettingsController();

        final CardLayout layout = new CardLayout(5, 5);

        final JPanel selectedGroupPanel = new JPanel(layout);
        final JScrollPane scrollPane = new JScrollPane(selectedGroupPanel);

        final Set<Group> groups = controller.getGroups();
        final String[] groupNames = groups.stream().map(Group::getGroupName).toArray(String[]::new);

        final JList<String> groupSelectorList = new JList<>(groupNames);

        groupSelectorList.addListSelectionListener(e -> {
            final String selectedGroup = groupSelectorList.getSelectedValue();
            if (selectedGroup != null) {
                layout.show(selectedGroupPanel, selectedGroup);
            }
        });

        groupSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (final Group group : groups) {
            final String name = group.getGroupName();

            final VerticalFlowLayout verticalLayout = new VerticalFlowLayout(VerticalFlowLayout.TOP, 5, 5);
            final JPanel settingPanel = new JPanel(verticalLayout);
            verticalLayout.setMaximizeOtherDimension(true);

            for (final Setting<?> setting : group.getSettings()) {
                final JComponent settingModel = factory.model(setting);
                if (settingModel != null) {
                    settingPanel.add(settingModel);
                }
            }

            selectedGroupPanel.add(settingPanel, name);
        }

        content.setBorder(new EmptyBorder(5, 5, 5, 5));
        content.add(groupSelectorList, BorderLayout.WEST);
        content.add(scrollPane, BorderLayout.CENTER);

        return content;
    }

    private JPanel buildControls() {
        final SettingsController controller = domain.getSettingsController();

        final JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        final JButton apply = new JButton("Apply");
        final JButton accept = new JButton("Accept");
        final JButton cancel = new JButton("Cancel");

        controls.add(apply);
        controls.add(accept);
        controls.add(cancel);

        apply.addActionListener(e -> {
            controller.getSettings().applyChanged();
        });

        accept.addActionListener(e -> {
            controller.getSettings().applyChanged();
            setVisible(false);
        });

        cancel.addActionListener(e -> {
            controller.getSettings().discardChanges();
            setVisible(false);
        });

        return controls;
    }

    @Override
    public void initialize() {
        buildComponents();
    }

    @Override
    public boolean isVisible() {
        return frame.isVisible();
    }

    @Override
    public void setVisible(final boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void dispose() {
        frame.dispose();
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
