package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.BytecodeElement;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */
public abstract class AnnotationTarget extends BytecodeElement {

    private final Set<Annotation> annotations = new LinkedHashSet<>();

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void add(final TypeAnnotation annotation) {
        annotations.add(annotation);
    }

    public abstract void walk(final TypeAnnotation annotation, final Iterator<Path> path);

}
