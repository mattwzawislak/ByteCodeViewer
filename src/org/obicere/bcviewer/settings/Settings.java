package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.settings.target.Setting;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class Settings {

    private final Map<String, Setting<?>> settings;

    private final Map<Setting<?>, Change> changeList = new HashMap<>();

    public Settings(final Map<String, Setting<?>> settings) {
        this.settings = settings;
    }

    protected Map<String, Setting<?>> getSettings() {
        return settings;
    }

    public boolean hasChanges() {
        return !changeList.isEmpty();
    }

    public void discardChanges() {
        changeList.clear();
    }

    public void applyChanged() {
        for (final Change change : changeList.values()) {
            change.apply();
        }
        changeList.clear();
    }

    public boolean isPresent(final String name) {
        return settings.get(name) != null;
    }

    public Object get(final String name) {
        final Setting<?> setting = settings.get(name);
        if (setting != null) {
            final Object value = setting.getValue();
            if (value != null) {
                return value;
            }
            return setting.getDefaultValue();
        }
        return null;
    }

    public Object get(final String name, final Object defaultValue) {
        final Object contained = get(name);
        if (contained != null) {
            return contained;
        } else {
            return defaultValue;
        }
    }

    public boolean getBoolean(final String name) {
        return (boolean) get(name, false);
    }

    public boolean getBoolean(final String name, final boolean defaultValue){
        return (boolean) get(name, defaultValue);
    }

    public int getInteger(final String name) {
        return (int) get(name, 0);
    }

    public int getInteger(final String name, final int defaultValue) {
        return (int) get(name, defaultValue);
    }

    public long getLong(final String name) {
        return (long) get(name, 0);
    }

    public long getLong(final String name, final long defaultValue) {
        return (long) get(name, defaultValue);
    }

    public double getDouble(final String name) {
        return (double) get(name, 0);
    }

    public double getDouble(final String name, final double defaultValue) {
        return (double) get(name, defaultValue);
    }

    public float getFloat(final String name) {
        return (float) get(name, 0);
    }

    public float getFloat(final String name, final float defaultValue) {
        return (float) get(name, defaultValue);
    }

    public Color getColor(final String name) {
        return (Color) get(name, Color.BLACK);
    }

    public Color getColor(final String name, final Color defaultValue) {
        return (Color) get(name, defaultValue);
    }

    public Font getFont(final String name) {
        return (Font) get(name, new QuickWidthFont(Font.MONOSPACED, Font.PLAIN, 14));
    }

    public Font getFont(final String name, final Font defaultValue) {
        return (Font) get(name, defaultValue);
    }

    public void set(final String name, final Object value) {
        if (isPresent(name)) {
            final Setting<?> setting = settings.get(name);
            changeList.put(setting, new Change(name, value));
        }
    }

    public void setToDefault(final String name) {
        final Setting<?> setting = settings.get(name);
        if (setting == null) {
            return;
        }
        final Object defaultValue = setting.getDefaultValue();
        if (defaultValue != null) {
            // remove the entry instead of setting it null
            changeList.put(setting, new Change(name, null));
        }
    }

    public void setAllToDefault() {
        for (final Map.Entry<String, Setting<?>> entry : settings.entrySet()) {
            final String key = entry.getKey();
            final Setting<?> setting = entry.getValue();
            final Object defaultValue = setting.getDefaultValue();
            changeList.put(setting, new Change(key, defaultValue));
        }
    }

    private class Change {

        private final String key;
        private final Object value;

        public Change(final String key, final Object value) {
            this.key = key;
            this.value = value;
        }

        public void apply() {
            final Setting<?> get = settings.get(key);
            get.setValue(value);
        }
    }
}
