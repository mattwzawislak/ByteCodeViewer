package org.obicere.bytecode.viewer.modeler;

import org.javacore.Identifiable;
import org.javacore.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class ModelerSet {

    private final HashMap<Identifier, Modeler<?>> modelers = new HashMap<>();

    public void add(final Identifier identifier, final Modeler<?> modeler) {
        if (identifier == null) {
            throw new NullPointerException("identifier must be non-null");
        }
        if (modeler == null) {
            throw new NullPointerException("modeler must be non-null");
        }

        modelers.put(identifier, modeler);
    }

    public boolean contains(final Identifier identifier) {
        return modelers.containsKey(identifier);
    }

    public Collection<Modeler<?>> getModelers() {
        return modelers.values();
    }

    @SuppressWarnings("unchecked")
    public <T extends Identifiable> Modeler<? super T> get(final Identifier identifier) {
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
