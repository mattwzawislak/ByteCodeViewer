package org.obicere.bcviewer.settings.handles;

/**
 */
public class StringHandle implements Handle<String> {

    @Override
    public String decode(final String property) {
        return property;
    }

    @Override
    public String encode(final String value) {
        return value;
    }
}
