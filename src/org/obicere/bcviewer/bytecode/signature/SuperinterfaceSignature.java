package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.DocumentBuilder;

import java.util.Iterator;

/**
 */
public class SuperinterfaceSignature extends AnnotationTarget {

    private final ClassTypeSignature classTypeSignature;

    private SuperinterfaceSignature(final ClassTypeSignature classTypeSignature) {
        this.classTypeSignature = classTypeSignature;
    }

    public ClassTypeSignature getClassTypeSignature() {
        return classTypeSignature;
    }

    public static SuperinterfaceSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final ClassTypeSignature classTypeSignature = ClassTypeSignature.parse(string);
        if (classTypeSignature == null) {
            return null;
        }
        return new SuperinterfaceSignature(classTypeSignature);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        classTypeSignature.walk(annotation, path);
    }

    @Override
    public void model(final DocumentBuilder builder){
        classTypeSignature.model(builder);
    }
}
