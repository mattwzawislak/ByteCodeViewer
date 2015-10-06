package org.obicere.bcviewer.settings.target;

/**
 */
public class DoubleSetting extends Setting<Double> {

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

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }
}
