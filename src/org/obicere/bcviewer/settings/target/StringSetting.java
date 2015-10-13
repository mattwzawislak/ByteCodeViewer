package org.obicere.bcviewer.settings.target;

/**
 */
public class StringSetting extends Setting<String> {

    public static final String IDENTIFIER = "StringModeler";

    public StringSetting(final String name, final String descriptor, final String value) {
        super(name, descriptor, value);
    }

    @Override
    public String getID() {
        return IDENTIFIER;
    }
}
