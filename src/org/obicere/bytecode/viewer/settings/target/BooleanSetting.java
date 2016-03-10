package org.obicere.bytecode.viewer.settings.target;

/**
 */
public class BooleanSetting extends Setting<Boolean> {

    public static final String IDENTIFIER = "Boolean";

    public BooleanSetting(final String name, final String descriptor, final Boolean value) {
        super(name, descriptor, value);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
