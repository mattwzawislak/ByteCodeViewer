package org.obicere.bcviewer.settings.target;

import java.awt.Color;

/**
 */
public class ColorSetting extends Setting<Color> {

    public static final String MODELER_ID = "ColorModeler";

    public ColorSetting(final String name, final String descriptor, final Color value) {
        super(name, descriptor, value);
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }
}
