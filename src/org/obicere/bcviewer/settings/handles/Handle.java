package org.obicere.bcviewer.settings.handles;

/**
 */
public interface Handle<T> {

    public T decode(final String value);

    public String encode(final T value);

}
