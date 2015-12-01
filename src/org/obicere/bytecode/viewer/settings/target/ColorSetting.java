package org.obicere.bytecode.viewer.settings.target;

import java.awt.Color;

/**
 */
public class ColorSetting extends Setting<Color> {

    public static final String IDENTIFIER = "ColorModeler";

    public ColorSetting(final String name, final String descriptor, final Color value) {
        super(name, descriptor, value);
    }

    @Override
    public String getID() {
        return IDENTIFIER;
    }
}
