package org.obicere.bcviewer.settings.target;

/**
 */
public class DoubleSetting extends Setting<Double> {

    public static final String MODELER_ID = "DoubleModeler";

    private final double minimum;
    private final double maximum;

    public DoubleSetting(final String name, final String descriptor, final Double value) {
        this(name, descriptor, value, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleSetting(final String name, final String descriptor, final Double value, final double minimum, final double maximum) {
        super(name, descriptor, value);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }
}
