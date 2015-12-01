package org.obicere.bytecode.viewer.settings.handles;

/**
 */
public interface Handle<T> {

    public T decode(final String value);

    public String encode(final Object value);

}
