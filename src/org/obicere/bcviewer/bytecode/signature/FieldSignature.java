package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 */
public class FieldSignature extends AnnotationTarget {

    private final JavaTypeSignature javaTypeSignature;

    private FieldSignature(final JavaTypeSignature javaTypeSignature) {
        this.javaTypeSignature = javaTypeSignature;
    }

    public JavaTypeSignature getJavaTypeSignature() {
        return javaTypeSignature;
    }

    public static FieldSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final JavaTypeSignature javaTypeSignature = JavaTypeSignature.parse(string);

        if (javaTypeSignature == null) {
            return null;
        }
        return new FieldSignature(javaTypeSignature);
    }

    public void addAnnotations(final TypeAnnotation[] types) {
        for (final TypeAnnotation type : types) {
            final List<Path> list = Arrays.asList(type.getTargetPath().getPath());
            walk(type, list.iterator());
        }
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        final int targetType = annotation.getTargetType();
        if (targetType == 0x13) { // empty_target
            javaTypeSignature.walk(annotation, path);
        }
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        javaTypeSignature.model(builder, parent);
    }
}
