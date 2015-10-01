package org.obicere.bcviewer.settings.handles;

/**
 */
public class DoubleHandle implements Handle<Double> {

    @Override
    public Double decode(final String property) {
        try {
            return Double.parseDouble(property);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Double value) {
        return Double.toString(value);
    }
}
