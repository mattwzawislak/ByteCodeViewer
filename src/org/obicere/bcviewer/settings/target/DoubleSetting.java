package org.obicere.bcviewer.settings.target;

/**
 */
public class DoubleSetting extends NumberSetting<Double> {

    public static final String MODELER_ID = "DoubleModeler";

    public DoubleSetting(final String name, final String descriptor, final Double value) {
        this(name, descriptor, value, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleSetting(final String name, final String descriptor, final Double value, final double minimum, final double maximum) {
        super(name, descriptor, value, minimum, maximum);
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }
}
