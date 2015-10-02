package org.obicere.bcviewer.settings.target;

import java.beans.PropertyChangeListener;

/**
 */
public abstract class Setting<T> {

    private PropertyChangeListener listener;

    private final String name;

    private T value;

    public Setting(final String name, final T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.listener = listener;
    }

    public PropertyChangeListener getPropertyChangeListener() {
        return listener;
    }

    public void removePropertyChangeListener() {
        listener = null;
    }

}
