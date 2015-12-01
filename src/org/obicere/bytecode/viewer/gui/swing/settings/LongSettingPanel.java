package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.settings.SettingsController;
import org.obicere.bytecode.viewer.settings.target.NumberSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 */
public class LongSettingPanel extends SettingPanel<Long> {

    private final JSpinner spinner;

    public LongSettingPanel(final Domain domain, final Setting<Long> setting) {
        super(setting);

        final SettingsController controller = domain.getSettingsController();

        final long minValue;
        final long maxValue;

        if (setting instanceof NumberSetting) {
            final NumberSetting<Long> longSetting = (NumberSetting<Long>) setting;
            minValue = longSetting.getMinValue();
            maxValue = longSetting.getMaxValue();
        } else {
            minValue = Long.MIN_VALUE;
            maxValue = Long.MAX_VALUE;
        }

        final JLabel descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel((long) controller.getSettings().getLong(setting.getName()), minValue, maxValue, 1));

        spinner.addChangeListener(e -> controller.getSettings().set(setting.getName(), spinner.getValue()));

        final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createHorizontalGlue());
        add(spinner);
    }

    @Override
    public void setValue(final Object value) {
        spinner.setValue(value);
    }
}
