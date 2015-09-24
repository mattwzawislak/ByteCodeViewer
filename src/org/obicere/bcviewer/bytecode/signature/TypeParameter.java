package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;
import java.util.LinkedList;

/**
 */
public class TypeParameter extends AnnotationTarget {

    private final String identifier;

    private final ClassBound classBound;

    private final InterfaceBound[] interfaceBounds;

    private TypeParameter(final String identifier, final ClassBound classBound, final InterfaceBound[] interfaceBounds) {
        this.identifier = identifier;
        this.classBound = classBound;
        this.interfaceBounds = interfaceBounds;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ClassBound getClassBound() {
        return classBound;
    }

    public InterfaceBound[] getInterfaceBounds() {
        return interfaceBounds;
    }

    public static TypeParameter parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final String identifier = string.nextIdentifier();
        if (identifier == null) {
            return null;
        }

        final ClassBound classBound = ClassBound.parse(string);
        if (classBound == null) {
            return null;
        }

        final LinkedList<InterfaceBound> interfaceBoundList = new LinkedList<>();
        while (string.hasNext() && string.peek() == ':') {
            final InterfaceBound interfaceBound = InterfaceBound.parse(string);
            if (interfaceBound == null) {
                return null;
            }
            interfaceBoundList.add(interfaceBound);
        }
        final InterfaceBound[] interfaceBounds = interfaceBoundList.toArray(new InterfaceBound[interfaceBoundList.size()]);
        return new TypeParameter(identifier, classBound, interfaceBounds);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            return;
        }
        final Path next = path.next();
        final int kind = next.getTypePathKind();
        if (kind == Path.KIND_TYPE_ARGUMENT) {
            final ReferenceTypeSignature referenceTypeSignature = classBound.getReferenceTypeSignature();
            if (referenceTypeSignature != null) {
                referenceTypeSignature.walk(annotation, path);
            }
        }
    }
}
