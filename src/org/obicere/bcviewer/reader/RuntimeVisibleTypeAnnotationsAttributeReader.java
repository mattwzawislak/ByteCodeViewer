package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeVisibleTypeAnnotationsAttributeReader implements Reader<RuntimeVisibleTypeAnnotationsAttribute> {

    private final TypeAnnotationReader annotation;

    public RuntimeVisibleTypeAnnotationsAttributeReader(final TypeAnnotationReader annotation) {
        this.annotation = annotation;
    }

    @Override
    public RuntimeVisibleTypeAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numAnnotations = input.readUnsignedShort();
        final TypeAnnotation[] annotations = new TypeAnnotation[numAnnotations];

        for (int i = 0; i < numAnnotations; i++) {
            annotations[i] = annotation.read(input);
        }
        return new RuntimeVisibleTypeAnnotationsAttribute(annotations);
    }
}
