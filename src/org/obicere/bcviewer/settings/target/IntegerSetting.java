package org.obicere.bcviewer.settings.target;

/**
 */
public class IntegerSetting extends NumberSetting<Integer> {

    public static final String IDENTIFIER = "IntegerModeler";

    public IntegerSetting(final String name, final String descriptor, final Integer value) {
        this(name, descriptor, value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerSetting(final String name, final String descriptor, final Integer value, final int minimum, final int maximum) {
        super(name, descriptor, value, minimum, maximum);
    }

    @Override
    public String getID() {
        return IDENTIFIER;
    }
}
