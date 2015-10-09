package org.obicere.bcviewer.settings.target;

/**
 */
public class FloatSetting extends Setting<Float> {

    public static final String MODELER_ID = "FloatModeler";

    private final float minimum;
    private final float maximum;

    public FloatSetting(final String name, final String descriptor, final Float value) {
        this(name, descriptor, value, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public FloatSetting(final String name, final String descriptor, final Float value, final float minimum, final float maximum) {
        super(name, descriptor, value);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }
}
