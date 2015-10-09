package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.target.IntegerSetting;
import org.obicere.bcviewer.settings.target.NumberSetting;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 */
public class IntegerSettingPanel extends SettingPanel<Integer> {

    private final JLabel descriptor;

    private final JSpinner spinner;

    public IntegerSettingPanel(final Domain domain, final Setting<Integer> setting) {
        super(setting);

        final SettingsController controller = domain.getSettingsController();

        final int minValue;
        final int maxValue;

        if (setting instanceof NumberSetting) {
            final NumberSetting<Integer> intSetting = (NumberSetting<Integer>) setting;
            minValue = intSetting.getMinValue();
            maxValue = intSetting.getMaxValue();
        } else {
            minValue = Integer.MIN_VALUE;
            maxValue = Integer.MAX_VALUE;
        }

        this.descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel((int) controller.getSettings().getInteger(setting.getName()), minValue, maxValue, 1));

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
