package org.obicere.bcviewer.settings.target;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */
public abstract class Setting<T> {

    private final Set<PropertyChangeListener> listeners = new LinkedHashSet<>();

    private final String name;

    private final String descriptor;

    private final T defaultValue;

    private T value;

    public Setting(final String name, final String descriptor, final T defaultValue) {
        this.name = name;
        this.descriptor = descriptor;
        this.defaultValue = defaultValue;
    }

    public abstract String getID();

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public T setValue(final Object value) {
        final T old = this.value;

        // will throw class cast exception if not valid type
        this.value = (T) value;

        firePropertyChangeEvent(old, value);
        return old;
    }

    private void firePropertyChangeEvent(final Object oldValue, final Object newValue) {
        final PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, newValue);
        for (final PropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {

        listeners.add(listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return listeners.toArray(new PropertyChangeListener[listeners.size()]);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        listeners.remove(listener);
    }
}
