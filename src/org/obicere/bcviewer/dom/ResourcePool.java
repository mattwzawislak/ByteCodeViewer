package org.obicere.bcviewer.dom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Obicere
 */
public class ResourcePool<T> {

    private final HashMap<String, T> pool = new HashMap<>();

    public void add(final String name, final T color) {
        pool.put(name, color);
    }

    public T get(final String name) {
        return pool.get(name);
    }

    public T get(final String name, final T defaultValue) {
        return pool.getOrDefault(name, defaultValue);
    }

    public Collection<T> getResources() {
        return pool.values();
    }

    public Set<String> getNames() {
        return pool.keySet();
    }
}
