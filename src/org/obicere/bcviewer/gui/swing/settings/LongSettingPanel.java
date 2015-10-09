package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.target.NumberSetting;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 */
public class LongSettingPanel extends SettingPanel<Long> {

    private final JLabel descriptor;

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

        this.descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel((long) controller.getSettings().getLong(setting.getName()), minValue, maxValue, 1));

        spinner.addChangeListener(e -> controller.getSettings().set(setting.getName(), spinner.getValue()));

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createVerticalGlue());
        add(spinner);
    }

    @Override
    public void setValue(final Object value) {
        spinner.setValue(value);
    }
}
