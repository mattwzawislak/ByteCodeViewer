package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.dom.literals.TypeElement;

import java.util.Iterator;

/**
 */
public class TypeVariableSignature extends ReferenceTypeSignature {

    private final String identifier;

    private TypeVariableSignature(final String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static TypeVariableSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != 'T') {
            return null;
        }
        final String identifier = string.nextIdentifier();
        if (identifier == null) {
            return null;
        }
        if (!string.hasNext() || string.next() != ';') {
            return null;
        }
        return new TypeVariableSignature(identifier);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (path.hasNext()) {
            return;
        }
        add(annotation);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : getAnnotations()) {
            annotation.model(builder, parent);
        }
        final TypeElement name = new TypeElement("identifier", identifier, builder);
        name.setRightPad(1);
        parent.add(name);

    }
}
