package org.obicere.bcviewer.settings.target;

/**
 */
public abstract class Setting<T> {

    private final String name;

    private T value;

    public Setting(final String name, final T value) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

}
