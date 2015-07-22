package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeInvisibleTypeAnnotationsAttributeReader implements Reader<RuntimeInvisibleTypeAnnotationsAttribute> {

    private final TypeAnnotationReader annotation;

    public RuntimeInvisibleTypeAnnotationsAttributeReader(final TypeAnnotationReader annotation) {
        this.annotation = annotation;
    }

    @Override
    public RuntimeInvisibleTypeAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numAnnotations = input.readUnsignedShort();
        final TypeAnnotation[] annotations = new TypeAnnotation[numAnnotations];

        for (int i = 0; i < numAnnotations; i++) {
            annotations[i] = annotation.read(input);
        }
        return new RuntimeInvisibleTypeAnnotationsAttribute(annotations);
    }
}
