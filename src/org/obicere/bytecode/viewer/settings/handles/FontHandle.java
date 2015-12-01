package org.obicere.bytecode.viewer.settings.handles;

import org.obicere.bytecode.viewer.dom.awt.QuickWidthFont;

import java.awt.Font;

/**
 */
public class FontHandle implements Handle<Font> {

    @Override
    public Font decode(final String property) {
        final String[] parts = property.split(",");
        if (parts.length != 2) {
            return null;
        }
        try {
            final String family = parts[0];
            final int size = Integer.parseInt(parts[1]);
            if (size <= 0) {
                return null;
            }
            return new QuickWidthFont(family, Font.PLAIN, size);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Object value) {
        final Font font = (Font) value;
        return font.getName() + "," + font.getSize();
    }
}
