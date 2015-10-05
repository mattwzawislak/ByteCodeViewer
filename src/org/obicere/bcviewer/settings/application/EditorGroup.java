package org.obicere.bcviewer.settings.application;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.style.Style;
import org.obicere.bcviewer.dom.style.StyleConstants;
import org.obicere.bcviewer.settings.Group;
import org.obicere.bcviewer.settings.target.ColorSetting;
import org.obicere.bcviewer.settings.target.FontSetting;
import org.obicere.bcviewer.settings.target.Setting;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 */
public class EditorGroup implements Group {

    private static final ColorSetting PLAIN_COLOR_SETTING      = new ColorSetting("plainColor", "Plain text color for editor", new Color(16, 16, 16));
    private static final ColorSetting ANNOTATION_COLOR_SETTING = new ColorSetting("annotationColor", "Annotation text color for editor", new Color(167, 185, 44));
    private static final ColorSetting COMMENT_COLOR_SETTING    = new ColorSetting("commentColor", "Comment text color for editor", new Color(188, 188, 188));
    private static final ColorSetting KEYWORD_COLOR_SETTING    = new ColorSetting("keywordColor", "Keyword text color for editor", new Color(227, 80, 0));
    private static final ColorSetting NUMBER_COLOR_SETTING     = new ColorSetting("numberColor", "Number literals text color for editor", new Color(86, 151, 250));
    private static final ColorSetting STRING_COLOR_SETTING     = new ColorSetting("stringColor", "String literals text color for editor", new Color(0, 128, 52));
    private static final ColorSetting TYPE_COLOR_SETTING       = new ColorSetting("typeColor", "Generic type text color for editor", new Color(86, 151, 250));
    private static final FontSetting  FONT_SETTING             = new FontSetting("font", "Font for the editor", new QuickWidthFont("Courier new", Font.PLAIN, 14));

    private static final Setting<?>[] SETTINGS = new Setting[]{
            PLAIN_COLOR_SETTING,
            ANNOTATION_COLOR_SETTING,
            COMMENT_COLOR_SETTING,
            KEYWORD_COLOR_SETTING,
            NUMBER_COLOR_SETTING,
            STRING_COLOR_SETTING,
            TYPE_COLOR_SETTING,
            FONT_SETTING
    };

    public EditorGroup() {
        final String suffix = getGroupName() + ".";
        setColorPropertyListener(suffix, PLAIN_COLOR_SETTING, StyleConstants.PLAIN);
        setColorPropertyListener(suffix, ANNOTATION_COLOR_SETTING, StyleConstants.ANNOTATION);
        setColorPropertyListener(suffix, COMMENT_COLOR_SETTING, StyleConstants.COMMENT);
        setColorPropertyListener(suffix, KEYWORD_COLOR_SETTING, StyleConstants.KEYWORD);
        setColorPropertyListener(suffix, NUMBER_COLOR_SETTING, StyleConstants.NUMBER);
        setColorPropertyListener(suffix, STRING_COLOR_SETTING, StyleConstants.STRING);
        setColorPropertyListener(suffix, TYPE_COLOR_SETTING, StyleConstants.TYPE);

        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.PLAIN);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.ANNOTATION);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.COMMENT);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.KEYWORD);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.NUMBER);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.STRING);
        setFontPropertyListener(suffix, FONT_SETTING, StyleConstants.TYPE);

        // we then need to set all the styles
        for (final Setting<?> setting : SETTINGS) {
            setting.getPropertyChangeListener().propertyChange(new PropertyChangeEvent(setting, suffix + setting.getName(), null, setting.getValue()));
        }
    }

    private void setColorPropertyListener(final String suffix, final ColorSetting setting, final Style style) {
        final PropertyChangeListener listener = buildColorListener(suffix + setting.getName(), style);
        setting.addPropertyChangeListener(listener);
    }

    private PropertyChangeListener buildColorListener(final String name, final Style style) {
        return evt -> {
            if (evt.getPropertyName().equals(name)) {
                style.setColor((Color) evt.getNewValue());
            }
        };
    }

    private void setFontPropertyListener(final String suffix, final FontSetting setting, final Style style) {
        final PropertyChangeListener listener = buildFontListener(suffix + setting.getName(), style);
        setting.addPropertyChangeListener(listener);
    }

    private PropertyChangeListener buildFontListener(final String name, final Style style) {
        return evt -> {
            if (evt.getPropertyName().equals(name)) {
                style.setFont((Font) evt.getNewValue());
            }
        };
    }

    @Override
    public String getGroupName() {
        return "editor";
    }

    @Override
    public Setting<?>[] getSettings() {
        return SETTINGS;
    }
}
