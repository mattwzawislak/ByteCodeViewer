package org.obicere.bcviewer.settings.handles;

import java.awt.Color;

/**
 */
public class ColorHandle implements Handle<Color> {

    @Override
    public Color decode(final String property) {
        final String[] parts = property.split(",");
        if (parts.length != 3) {
            return null;
        }
        try {
            final int red = Integer.parseInt(parts[0]);
            final int green = Integer.parseInt(parts[1]);
            final int blue = Integer.parseInt(parts[2]);
            if (outOfRange(red) || outOfRange(green) || outOfRange(blue)) {
                return null;
            }
            return new Color(red, green, blue);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean outOfRange(final int value) {
        return 0 > value || value > 255;
    }

    @Override
    public String encode(final Object value) {
        final Color color = (Color) value;
        // 11 = length("rrr,ggg,bbb")
        final StringBuilder builder = new StringBuilder(11);
        builder.append(color.getRed());
        builder.append(',');
        builder.append(color.getGreen());
        builder.append(',');
        builder.append(color.getBlue());
        return builder.toString();
    }
}
