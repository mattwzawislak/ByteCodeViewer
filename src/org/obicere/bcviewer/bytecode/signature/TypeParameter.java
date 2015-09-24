package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

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
            add(annotation);
            return;
        }
        final Path next = path.next();
        final int kind = next.getTypePathKind();
        if (kind == Path.KIND_TYPE_ARGUMENT) {
            final int typeIndex = next.getTypeArgumentIndex();
            if (typeIndex == 0) {
                final ReferenceTypeSignature referenceTypeSignature = classBound.getReferenceTypeSignature();
                if (referenceTypeSignature != null) {
                    referenceTypeSignature.walk(annotation, path);
                } else {
                    interfaceBounds[0].walk(annotation, path);
                }
            } else {
                final int fixedIndex;
                if (classBound.getReferenceTypeSignature() != null) {
                    fixedIndex = typeIndex - 1;
                } else {
                    fixedIndex = typeIndex;
                }
                interfaceBounds[fixedIndex].walk(annotation, path);
            }
        }
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : getAnnotations()) {
            annotation.model(builder, parent);
        }
        final PlainElement element = new PlainElement("identifier", identifier, builder);
        parent.add(element);

        boolean classModeled = false;
        final ReferenceTypeSignature classReference = classBound.getReferenceTypeSignature();
        if (classReference != null) {
            final KeywordElement extendsKeyword = new KeywordElement("extends", "extends", builder);
            extendsKeyword.setLeftPad(1);
            extendsKeyword.setRightPad(1);
            parent.add(extendsKeyword);
            classReference.model(builder, parent);
            classModeled = true;
        }

        for (final InterfaceBound bound : interfaceBounds) {
            if (!classModeled) {
                final KeywordElement extendsKeyword = new KeywordElement("extends", "extends", builder);
                extendsKeyword.setLeftPad(1);
                extendsKeyword.setRightPad(1);
                parent.add(extendsKeyword);
            } else {
                final PlainElement and = new PlainElement("and", "&", builder);
                and.setLeftPad(1);
                and.setRightPad(1);
                parent.add(and);
            }
            final ReferenceTypeSignature interfaceReference = bound.getReferenceTypeSignature();
            interfaceReference.model(builder, parent);
        }
    }
}
