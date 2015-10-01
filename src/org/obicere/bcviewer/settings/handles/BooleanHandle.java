package org.obicere.bcviewer.settings.handles;

/**
 */
public class BooleanHandle implements Handle<Boolean> {

    @Override
    public Boolean decode(final String property) {
        return Boolean.getBoolean(property);
    }

    @Override
    public String encode(final Boolean value) {
        return Boolean.toString(value);
    }
}
