package org.obicere.bcviewer.settings.handles;

/**
 */
public class BooleanHandle implements Handle<Boolean> {

    @Override
    public Boolean decode(final String property) {
        return Boolean.parseBoolean(property);
    }

    @Override
    public String encode(final Object value) {
        return Boolean.toString((Boolean) value);
    }
}
