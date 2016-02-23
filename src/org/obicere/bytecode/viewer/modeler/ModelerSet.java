package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ByteCodeElement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class ModelerSet {

    private final HashMap<String, Modeler<?>> modelers = new HashMap<>();

    public void add(final String identifier, final Modeler<?> modeler) {
        if (identifier == null) {
            throw new NullPointerException("identifier must be non-null");
        }
        if (modeler == null) {
            throw new NullPointerException("modeler must be non-null");
        }

        modelers.put(identifier, modeler);
    }

    public boolean contains(final String identifier) {
        return modelers.containsKey(identifier);
    }

    public Set<String> getIdentifiers() {
        return modelers.keySet();
    }

    public Collection<Modeler<?>> getModelers() {
        return modelers.values();
    }

    @SuppressWarnings("unchecked")
    public <T extends ByteCodeElement> Modeler<? super T> get(final String identifier) {
        try {
            final Modeler<?> modeler = modelers.get(identifier);
            if (modeler == null) {
                Logger.getGlobal().warning("No modeler for identifier: " + identifier);
                return null;
            }

            // unchecked, but should be covered by the ClassCastException
            return (Modeler<? super T>) modeler;
        } catch (final ClassCastException e) {
            // the modeler for the identifier of T cannot actually model
            // a value of T. Basically, whomever added that modeler had no
            // regard for making it work
            Logger.getGlobal().severe("Modeler for identifier: " + identifier + " cannot be used as a modeler for given type");
            e.printStackTrace();
            return null;
        }
    }

}
