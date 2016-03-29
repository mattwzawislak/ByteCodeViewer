package org.obicere.bytecode.viewer.configuration;

import org.obicere.bytecode.viewer.context.ClassInformation;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class ClassStorage {

    private final Map<String, ClassInformation> cache = new ConcurrentHashMap<>();

    public void publish(final String name, final ClassInformation classInformation) {
        cache.put(name, classInformation);
    }

    public ClassInformation getClass(final String name) {
        return cache.get(name);
    }

    public void clear() {
        cache.clear();
    }

    public Set<String> getClassNames() {
        return cache.keySet();
    }
}
