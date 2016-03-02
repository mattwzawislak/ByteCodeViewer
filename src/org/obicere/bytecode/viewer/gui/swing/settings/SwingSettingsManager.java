package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.gui.SettingsManager;
import org.obicere.bytecode.viewer.gui.settings.SettingModelFactory;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.gui.swing.VerticalFlowLayout;
import org.obicere.bytecode.viewer.settings.Group;
import org.obicere.bytecode.viewer.settings.SettingsController;
import org.obicere.bytecode.viewer.settings.target.BooleanSetting;
import org.obicere.bytecode.viewer.settings.target.ColorSetting;
import org.obicere.bytecode.viewer.settings.target.DoubleSetting;
import org.obicere.bytecode.viewer.settings.target.FloatSetting;
import org.obicere.bytecode.viewer.settings.target.FontSetting;
import org.obicere.bytecode.viewer.settings.target.IntegerSetting;
import org.obicere.bytecode.viewer.settings.target.LongSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;
import org.obicere.bytecode.viewer.settings.target.StringSetting;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
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

        factory.addModeler(BooleanSetting.IDENTIFIER, new BooleanSettingModeler(domain));
        factory.addModeler(ColorSetting.IDENTIFIER, new ColorSettingModeler(domain));
        factory.addModeler(DoubleSetting.IDENTIFIER, new DoubleSettingModeler(domain));
        factory.addModeler(FloatSetting.IDENTIFIER, new FloatSettingModeler(domain));
        factory.addModeler(FontSetting.IDENTIFIER, new FontSettingModeler(domain));
        factory.addModeler(IntegerSetting.IDENTIFIER, new IntegerSettingModeler(domain));
        factory.addModeler(LongSetting.IDENTIFIER, new LongSettingModeler(domain));
        factory.addModeler(StringSetting.IDENTIFIER, new StringSettingModeler(domain));
    }

    @Override
    public void addModeler(final String id, final SettingModeler<?, JComponent> modeler) {
        factory.addModeler(id, modeler);
    }

    private void buildComponents() {
        final JPanel content = new JPanel(new BorderLayout(10, 10));

        final JPanel settings = buildSettings();
        final JPanel controls = buildControls();

        content.add(settings, BorderLayout.CENTER);
        content.add(controls, BorderLayout.SOUTH);

        frame.add(content);
        frame.pack();
        frame.setLocationByPlatform(true);
    }

    private JPanel buildSettings() {
        final JPanel content = new JPanel(new BorderLayout(5, 5));

        final SettingsController controller = domain.getSettingsController();

        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        final Set<Group> groups = controller.getGroups();

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

            tabbedPane.add(name, settingPanel);
        }

        content.setBorder(new EmptyBorder(5, 5, 5, 5));
        content.add(tabbedPane, BorderLayout.CENTER);

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

        apply.addActionListener(e -> controller.getSettings().applyChanged());

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
