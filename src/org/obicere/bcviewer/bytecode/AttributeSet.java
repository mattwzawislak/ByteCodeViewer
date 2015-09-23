package org.obicere.bcviewer.bytecode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class AttributeSet {

    private final HashMap<Class<? extends Attribute>, Set<Attribute>> map;

    public AttributeSet(final Attribute[] attributes) {
        final HashMap<Class<? extends Attribute>, Set<Attribute>> map = new HashMap<>();
        for (final Attribute attribute : attributes) {
            final Class<? extends Attribute> cls = attribute.getClass();
            final Set<Attribute> set = map.get(cls);
            final Set<Attribute> setToAddTo;
            if (set == null) {
                setToAddTo = new HashSet<>();
                map.put(cls, setToAddTo);
            } else {
                setToAddTo = set;
            }
            setToAddTo.add(attribute);
        }
        this.map = map;
    }

    @SuppressWarnings("unchecked")
    public <T extends Attribute> Set<T> getAttributes(final Class<T> cls) {
        // This should be checked as the attributes are divided up by name
        // when loading. Therefore if this isn't null, then every element
        // in the set will be of type T.
        return (Set<T>) map.get(cls);
    }

}
