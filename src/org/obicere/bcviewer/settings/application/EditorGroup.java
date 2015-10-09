package org.obicere.bcviewer.settings.application;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.style.Style;
import org.obicere.bcviewer.dom.style.StyleConstants;
import org.obicere.bcviewer.settings.Group;
import org.obicere.bcviewer.settings.target.BooleanSetting;
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

    private static final String NAME = "Editor Settings";

    private static final String SUFFIX = "editor.";

    private static final ColorSetting   PLAIN_COLOR_SETTING      = new ColorSetting(SUFFIX + "plainColor", "Plain text color", new Color(16, 16, 16));
    private static final ColorSetting   ANNOTATION_COLOR_SETTING = new ColorSetting(SUFFIX + "annotationColor", "Annotation text color", new Color(167, 185, 44));
    private static final ColorSetting   COMMENT_COLOR_SETTING    = new ColorSetting(SUFFIX + "commentColor", "Comment text color", new Color(188, 188, 188));
    private static final ColorSetting   KEYWORD_COLOR_SETTING    = new ColorSetting(SUFFIX + "keywordColor", "Keyword text color", new Color(227, 80, 0));
    private static final ColorSetting   NUMBER_COLOR_SETTING     = new ColorSetting(SUFFIX + "numberColor", "Number literals text color", new Color(86, 151, 250));
    private static final ColorSetting   STRING_COLOR_SETTING     = new ColorSetting(SUFFIX + "stringColor", "String literals text color", new Color(0, 128, 52));
    private static final ColorSetting   TYPE_COLOR_SETTING       = new ColorSetting(SUFFIX + "typeColor", "Generic type text color", new Color(86, 151, 250));

    private static final FontSetting    FONT_SETTING             = new FontSetting(SUFFIX + "font", "Standard font", new QuickWidthFont("Courier new", Font.PLAIN, 14));

    private static final BooleanSetting TEXT_ANTI_ALIASING       = new BooleanSetting(SUFFIX + "textAA", "Text anti-aliasing", true);
    private static final BooleanSetting COLOR_ANTI_ALIASING      = new BooleanSetting(SUFFIX + "colorAA", "Color anti-aliasing", true);

    private static final Setting<?>[] SETTINGS = new Setting[]{
            PLAIN_COLOR_SETTING,
            ANNOTATION_COLOR_SETTING,
            COMMENT_COLOR_SETTING,
            KEYWORD_COLOR_SETTING,
            NUMBER_COLOR_SETTING,
            STRING_COLOR_SETTING,
            TYPE_COLOR_SETTING,

            FONT_SETTING,

            TEXT_ANTI_ALIASING,
            COLOR_ANTI_ALIASING
    };

    public EditorGroup() {
        setColorPropertyListener(PLAIN_COLOR_SETTING, StyleConstants.PLAIN);
        setColorPropertyListener(ANNOTATION_COLOR_SETTING, StyleConstants.ANNOTATION);
        setColorPropertyListener(COMMENT_COLOR_SETTING, StyleConstants.COMMENT);
        setColorPropertyListener(KEYWORD_COLOR_SETTING, StyleConstants.KEYWORD);
        setColorPropertyListener(NUMBER_COLOR_SETTING, StyleConstants.NUMBER);
        setColorPropertyListener(STRING_COLOR_SETTING, StyleConstants.STRING);
        setColorPropertyListener(TYPE_COLOR_SETTING, StyleConstants.TYPE);

        setFontPropertyListener(FONT_SETTING, StyleConstants.PLAIN);
        setFontPropertyListener(FONT_SETTING, StyleConstants.ANNOTATION);
        setFontPropertyListener(FONT_SETTING, StyleConstants.COMMENT);
        setFontPropertyListener(FONT_SETTING, StyleConstants.KEYWORD);
        setFontPropertyListener(FONT_SETTING, StyleConstants.NUMBER);
        setFontPropertyListener(FONT_SETTING, StyleConstants.STRING);
        setFontPropertyListener(FONT_SETTING, StyleConstants.TYPE);

        // we then need to set all the styles
        for (final Setting<?> setting : SETTINGS) {
            final PropertyChangeListener[] listeners = setting.getPropertyChangeListeners();
            for (final PropertyChangeListener listener : listeners) {
                listener.propertyChange(new PropertyChangeEvent(setting, setting.getName(), null, setting.getDefaultValue()));
            }
        }
    }

    private void setColorPropertyListener(final ColorSetting setting, final Style style) {
        final PropertyChangeListener listener = buildColorListener(setting.getName(), style);
        setting.addPropertyChangeListener(listener);
    }

    private PropertyChangeListener buildColorListener(final String name, final Style style) {
        return evt -> {
            if (evt.getPropertyName().equals(name)) {
                style.setColor((Color) evt.getNewValue());
            }
        };
    }

    private void setFontPropertyListener(final FontSetting setting, final Style style) {
        final PropertyChangeListener listener = buildFontListener(setting.getName(), style);
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
        return NAME;
    }

    @Override
    public Setting<?>[] getSettings() {
        return SETTINGS;
    }
}
