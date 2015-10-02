package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.settings.target.Setting;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;

/**
 */
public class Settings {

    private final HashMap<String, Setting<?>> settings;

    private final HashMap<String, Object> currentMap;

    private final LinkedList<Change> changeList = new LinkedList<>();

    private PropertyChangeListener listener;

    public Settings(final HashMap<String, Setting<?>> settings, final HashMap<String, Object> currentMap) {
        this.settings = settings;
        this.currentMap = currentMap;
    }

    HashMap<String, Setting<?>> getSettings() {
        return settings;
    }

    HashMap<String, Object> getCurrentMap() {
        return currentMap;
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        this.listener = listener;
    }

    public PropertyChangeListener getPropertyChangeListener() {
        return listener;
    }

    public void removePropertyChangeListener() {
        listener = null;
    }

    private void firePropertyChangeEvent(final String key, final Object oldValue, final Object newValue) {
        if (listener == null) {
            return;
        }
        final Setting<?> setting = settings.get(key);
        if (setting == null) {
            return;
        }
        final PropertyChangeEvent event = new PropertyChangeEvent(setting, key, oldValue, newValue);
        final PropertyChangeListener settingListener = setting.getPropertyChangeListener();
        if (settingListener != null) {
            settingListener.propertyChange(event);
        }
        listener.propertyChange(event);
    }

    public boolean hasChanges() {
        return !changeList.isEmpty();
    }

    public void discardChanges() {
        changeList.clear();
    }

    public void applyChanged() {
        while (!changeList.isEmpty()) {
            changeList.poll().apply();
        }
    }

    public boolean isPresent(final String name) {
        return currentMap.get(name) != null || settings.get(name) != null;
    }

    public Object get(final String name) {
        final Object value = currentMap.get(name);
        if (value == null) {
            final Setting<?> setting = settings.get(name);
            if (setting == null) {
                return null;
            }
            return setting.getValue();
        }
        return value;
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

    public Color getColor(final String name){
        return (Color) get(name);
    }

    public Font getFont(final String name){
        return (Font) get(name);
    }

    public void set(final String name, final Object value) {
        if (isPresent(name)) {
            changeList.add(new Change(name, value));
        }
    }

    public void setToDefault(final String name) {
        final Setting<?> setting = settings.get(name);
        if (setting == null) {
            return;
        }
        final Object defaultValue = setting.getValue();
        if (defaultValue != null) {
            // remove the entry instead of setting it null
            changeList.add(new Change(name, defaultValue));
        }
    }

    public void setAllToDefault() {
        for (final String key : currentMap.keySet()) {
            final Setting<?> setting = settings.get(key);
            if (setting == null) {
                continue;
            }
            final Object defaultValue = setting.getValue();
            changeList.add(new Change(key, defaultValue));
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
            final Object old = currentMap.put(key, value);
            firePropertyChangeEvent(key, old, value);
        }
    }
}
