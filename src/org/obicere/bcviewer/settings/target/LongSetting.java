package org.obicere.bcviewer.settings.target;

/**
 */
public class LongSetting extends Setting<Long> {

    public static final String MODELER_ID = "LongModeler";

    private final long minimum;
    private final long maximum;

    public LongSetting(final String name, final String descriptor, final Long value) {
        this(name, descriptor, value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongSetting(final String name, final String descriptor, final Long value, final long minimum, final long maximum) {
        super(name, descriptor, value);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }

    public long getMinimum() {
        return minimum;
    }

    public long getMaximum() {
        return maximum;
    }
}
