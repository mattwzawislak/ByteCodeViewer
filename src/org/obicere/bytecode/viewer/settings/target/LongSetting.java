package org.obicere.bytecode.viewer.settings.target;

/**
 */
public class LongSetting extends NumberSetting<Long> {

    public static final String IDENTIFIER = "Long";

    public LongSetting(final String name, final String descriptor, final Long value) {
        this(name, descriptor, value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongSetting(final String name, final String descriptor, final Long value, final long minimum, final long maximum) {
        super(name, descriptor, value, minimum, maximum);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
