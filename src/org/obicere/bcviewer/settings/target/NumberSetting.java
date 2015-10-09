package org.obicere.bcviewer.settings.target;

/**
 */
public abstract class NumberSetting<N extends Number> extends Setting<N> {

    private final N minValue;

    private final N maxValue;

    public NumberSetting(final String name, final String descriptor, final N defaultValue, final N minValue, final N maxValue) {
        super(name, descriptor, defaultValue);
        if (minValue == null) {
            throw new NullPointerException("min value must be non-null.");
        }
        if (maxValue == null) {
            throw new NullPointerException("max value must be non-null.");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public N getMinValue() {
        return minValue;
    }

    public N getMaxValue() {
        return maxValue;
    }
}
