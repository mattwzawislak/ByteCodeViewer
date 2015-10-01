package org.obicere.bcviewer.settings.handles;

/**
 */
public class FloatHandle implements Handle<Float> {

    @Override
    public Float decode(final String property) {
        try {
            return Float.parseFloat(property);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Float value) {
        return Float.toString(value);
    }
}
