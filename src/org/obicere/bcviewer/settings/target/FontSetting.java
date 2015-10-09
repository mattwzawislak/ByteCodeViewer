package org.obicere.bcviewer.settings.target;

import java.awt.Font;

/**
 */
public class FontSetting extends Setting<Font> {

    public static final String MODELER_ID = "FontModeler";

    public FontSetting(final String name, final String descriptor, final Font value) {
        super(name, descriptor, value);
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }
}
