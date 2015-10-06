package org.obicere.bcviewer.settings.target;

/**
 */
public class IntegerSetting extends Setting<Integer> {

    private final int minimum;

    private final int maximum;

    public IntegerSetting(final String name, final String descriptor, final Integer value) {
        this(name, descriptor, value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerSetting(final String name, final String descriptor, final Integer value, final int minimum, final int maximum) {
        super(name, descriptor, value);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }
}
