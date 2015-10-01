package org.obicere.bcviewer.settings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;

/**
 */
public class Settings {

    private final HashMap<String, Object> defaultsMap;

    private final HashMap<String, Object> settings;

    private final LinkedList<Change> changeList = new LinkedList<>();

    private PropertyChangeListener listener;

    public Settings(final HashMap<String, Object> defaultsMap, final HashMap<String, Object> settings){
        this.defaultsMap = defaultsMap;
        this.settings = settings;
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

    private void firePropertyChangeEvent(final String key, final Object oldValue, final Object newValue) {
        if (listener == null) {
            return;
        }
        final PropertyChangeEvent event = new PropertyChangeEvent(this, key, oldValue, newValue);
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
        return settings.get(name) != null || defaultsMap.get(name) != null;
    }

    public Object get(final String name) {
        final Object value = settings.get(name);
        if (value == null) {
            return defaultsMap.get(name);
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

    public void set(final String name, final Object value) {
        if (isPresent(name)) {
            changeList.add(new SetChange(name, value));
        }
    }

    public void setToDefault(final String name) {
        final Object defaultValue = defaultsMap.get(name);
        if (defaultValue != null) {
            // remove the entry instead of setting it null
            changeList.add(new RemoveChange(name));
        }
    }

    public void setAllToDefault() {
        for (final String key : settings.keySet()) {
            changeList.add(new RemoveChange(key));
        }
    }

    private interface Change {

        public void apply();

    }

    private class SetChange implements Change {

        private final String key;
        private final Object value;

        public SetChange(final String key, final Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void apply() {
            final Object old = settings.put(key, value);
            firePropertyChangeEvent(key, old, value);
        }
    }

    private class RemoveChange implements Change {

        private final String key;

        public RemoveChange(final String key) {
            this.key = key;
        }

        @Override
        public void apply() {
            final Object old = settings.remove(key);
            firePropertyChangeEvent(key, old, defaultsMap.get(key));
        }

    }
}
