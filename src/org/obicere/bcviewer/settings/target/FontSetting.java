package org.obicere.bcviewer.settings.target;

import java.awt.Font;

/**
 */
public class FontSetting extends Setting<Font> {

    public static final String IDENTIFIER = "FontModeler";

    public FontSetting(final String name, final String descriptor, final Font value) {
        super(name, descriptor, value);
    }

    @Override
    public String getID() {
        return IDENTIFIER;
    }
}
