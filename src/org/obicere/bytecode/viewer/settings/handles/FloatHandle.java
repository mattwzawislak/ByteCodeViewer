package org.obicere.bytecode.viewer.settings.handles;

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
    public String encode(final Object value) {
        return Float.toString((Float) value);
    }
}
