package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.settings.target.Setting;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class Settings {

    private final HashMap<String, Setting<?>> settings;

    private final HashMap<Setting<?>, Change> changeList = new HashMap<>();

    public Settings(final HashMap<String, Setting<?>> settings) {
        this.settings = settings;
    }

    HashMap<String, Setting<?>> getSettings() {
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

    public Boolean getBoolean(final String name) {
        return (Boolean) get(name);
    }

    public Integer getInteger(final String name) {
        return (Integer) get(name);
    }

    public Long getLong(final String name) {
        return (Long) get(name);
    }

    public Double getDouble(final String name) {
        return (Double) get(name);
    }

    public Float getFloat(final String name) {
        return (Float) get(name);
    }

    public Color getColor(final String name) {
        return (Color) get(name);
    }

    public Font getFont(final String name) {
        return (Font) get(name);
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
