package org.obicere.bcviewer.settings.handles;

/**
 */
public class LongHandle implements Handle<Long> {

    @Override
    public Long decode(final String property) {
        try {
            return Long.decode(property);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Object value) {
        return Long.toString((Long) value);
    }
}
