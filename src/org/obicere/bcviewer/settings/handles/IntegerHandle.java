package org.obicere.bcviewer.settings.handles;

/**
 */
public class IntegerHandle implements Handle<Integer> {

    @Override
    public Integer decode(final String property) {
        try {
            return Integer.decode(property);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Integer value) {
        return Integer.toString(value);
    }
}
