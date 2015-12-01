package org.obicere.bytecode.viewer.settings.handles;

/**
 */
public class StringHandle implements Handle<String> {

    @Override
    public String decode(final String property) {
        return property;
    }

    @Override
    public String encode(final Object value) {
        return (String) value;
    }
}
