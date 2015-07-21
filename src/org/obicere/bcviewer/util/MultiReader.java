package org.obicere.bcviewer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Obicere
 */
public abstract class MultiReader<K, T> implements Reader<T> {

    private final Map<K, Reader<? extends T>> readers = new HashMap<>();

    public final Reader<? extends T> get(final K id){
        return readers.get(id);
    }

    public final void add(final K id, final Reader<? extends T> reader) {
        if (reader == null) {
            throw new NullPointerException("reader cannot be null.");
        }
        readers.put(id, reader);
    }

    public final boolean remove(final K opcode) {
        return readers.remove(opcode) != null;
    }

    public final boolean remove(final Reader<? extends T> reader) {
        boolean removed = false;
        for (final Map.Entry<K, Reader<? extends T>> entry : readers.entrySet()) {
            if (entry.getValue() == reader || entry.getValue().equals(reader)) {
                readers.remove(entry.getKey());
                removed = true;
            }
        }
        return removed;
    }
}
