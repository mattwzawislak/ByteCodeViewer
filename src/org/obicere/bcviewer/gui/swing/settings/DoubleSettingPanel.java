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
public class DoubleSettingPanel extends SettingPanel<Double> {

    private final JLabel descriptor;

    private final JSpinner spinner;

    public DoubleSettingPanel(final Domain domain, final Setting<Double> setting) {
        super(setting);

        final SettingsController controller = domain.getSettingsController();

        final double minValue;
        final double maxValue;

        if (setting instanceof NumberSetting) {
            final NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
            minValue = doubleSetting.getMinValue();
            maxValue = doubleSetting.getMaxValue();
        } else {
            minValue = Double.MIN_VALUE;
            maxValue = Double.MAX_VALUE;
        }

        this.descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel((double) controller.getSettings().getDouble(setting.getName()), minValue, maxValue, 1));

        spinner.addChangeListener(e -> controller.getSettings().set(setting.getName(), spinner.getValue()));

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
