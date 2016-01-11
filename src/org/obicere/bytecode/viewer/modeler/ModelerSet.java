package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.AnnotationElementValue;
import org.obicere.bytecode.core.objects.AppendFrame;
import org.obicere.bytecode.core.objects.ArrayElementValue;
import org.obicere.bytecode.core.objects.BooleanElementValue;
import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.ByteCodeElement;
import org.obicere.bytecode.core.objects.ByteElementValue;
import org.obicere.bytecode.core.objects.CharacterElementValue;
import org.obicere.bytecode.core.objects.ChopFrame;
import org.obicere.bytecode.core.objects.ClassElementValue;
import org.obicere.bytecode.core.objects.ClassFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class ModelerSet {

    private final HashMap<String, Modeler<?>> modelers = new HashMap<>();

    public ModelerSet() {
        // Types
        addSafe(Annotation.IDENTIFIER, new AnnotationModeler());
        addSafe(BootstrapMethod.IDENTIFIER, new BootstrapMethodModeler());
        addSafe(ClassFile.IDENTIFIER, new ClassFileModeler());

        // Attributes
        addSafe(AnnotationDefaultAttribute.IDENTIFIER, new AnnotationDefaultAttributeModeler());
        addSafe(BootstrapMethodsAttribute.IDENTIFIER, new BootstrapMethodsAttributeModeler());

        // Frames
        addSafe(AppendFrame.IDENTIFIER, new AppendFrameModeler());
        addSafe(ChopFrame.IDENTIFIER, new ChopFrameModeler());

        // Annotation element value pair types
        addSafe(AnnotationElementValue.IDENTIFIER, new AnnotationElementValueModeler());
        addSafe(ArrayElementValue.IDENTIFIER, new ArrayElementValueModeler());
        addSafe(BooleanElementValue.IDENTIFIER, new BooleanElementValueModeler());
        addSafe(ByteElementValue.IDENTIFIER, new ByteElementValueModeler());
        addSafe(CharacterElementValue.IDENTIFIER, new CharacterElementValueModeler());
        addSafe(ClassElementValue.IDENTIFIER, new ClassElementValueModeler());
    }

    public void add(final String identifier, final Modeler<?> modeler) {
        if (identifier == null) {
            throw new NullPointerException("identifier must be non-null");
        }
        if (modeler == null) {
            throw new NullPointerException("modeler must be non-null");
        }

        addSafe(identifier, modeler);
    }

    private void addSafe(final String identifier, final Modeler<?> modeler) {
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
    public <T extends ByteCodeElement> Modeler<T> get(final String identifier) {
        try {
            final Modeler<?> modeler = modelers.get(identifier);
            if (modeler == null) {
                Logger.getGlobal().warning("No modeler for identifier: " + identifier);
                return null;
            }

            // unchecked, but should be covered by the ClassCastException
            return (Modeler<T>) modeler;
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
