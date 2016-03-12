package org.obicere.bytecode.viewer.settings.target;

/**
 */
public class StringSetting extends Setting<String> {

    public static final String IDENTIFIER = "String";

    public StringSetting(final String name, final String descriptor, final String value) {
        super(name, descriptor, value);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
