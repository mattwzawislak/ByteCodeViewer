package org.obicere.bcviewer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Obicere
 */
public abstract class MultiReader<T> implements Reader<T> {

    private final Map<Integer, Reader<? extends T>> readers = new HashMap<>();

    public final Reader<? extends T> get(final int id){
        return readers.get(id);
    }

    public final void add(final int opcode, final Reader<? extends T> reader) {
        if (reader == null) {
            throw new NullPointerException("reader cannot be null.");
        }
        readers.put(opcode, reader);
    }

    public final boolean remove(final int opcode) {
        return readers.remove(opcode) != null;
    }

    public final boolean remove(final Reader<? extends T> reader) {
        boolean removed = false;
        for (final Map.Entry<Integer, Reader<? extends T>> entry : readers.entrySet()) {
            if (entry.getValue() == reader || entry.getValue().equals(reader)) {
                readers.remove(entry.getKey());
                removed = true;
            }
        }
        return removed;
    }
}
