package org.obicere.bcviewer.settings.target;

/**
 */
public class IntegerSetting extends Setting<Integer> {

    public static final String MODELER_ID = "IntegerModeler";

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

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }
}
