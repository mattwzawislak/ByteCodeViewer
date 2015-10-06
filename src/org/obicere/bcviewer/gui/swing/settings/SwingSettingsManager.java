package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.settings.SettingModelFactory;
import org.obicere.bcviewer.settings.Group;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.target.Setting;
import org.obicere.utility.swing.VerticalFlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.Set;

/**
 */
public class SwingSettingsManager implements DomainAccess {

    private final SettingModelFactory<JComponent> factory = new SettingModelFactory<>();

    private final JFrame frame;

    private final Domain domain;

    public SwingSettingsManager(final Domain domain) {
        this.domain = domain;

        this.frame = new JFrame("Settings");

        buildComponents();
    }

    private void buildComponents() {

        final JPanel settings = buildSettings();
        final JPanel controls = buildControls();

        frame.add(settings, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
    }

    private JPanel buildSettings() {
        final JPanel content = new JPanel(new BorderLayout(5, 5));

        final SettingsController controller = domain.getSettingsController();

        final CardLayout layout = new CardLayout(5, 5);

        final JPanel selectedGroupPanel = new JPanel(layout);

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

        content.add(groupSelectorList, BorderLayout.WEST);
        content.add(selectedGroupPanel, BorderLayout.CENTER);

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
        });

        return controls;
    }

    public void setVisible(final boolean visible) {
        frame.setVisible(visible);
    }

    public void dispose() {
        frame.dispose();
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
