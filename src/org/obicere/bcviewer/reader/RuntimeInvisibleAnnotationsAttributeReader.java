package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeInvisibleAnnotationsAttributeReader implements Reader<RuntimeInvisibleAnnotationsAttribute> {

    private final AnnotationReader annotation;

    public RuntimeInvisibleAnnotationsAttributeReader(final AnnotationReader annotation) {
        this.annotation = annotation;
    }

    @Override
    public RuntimeInvisibleAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numAnnotations = input.readUnsignedShort();
        final Annotation[] annotations = new Annotation[numAnnotations];

        for (int i = 0; i < numAnnotations; i++) {
            annotations[i] = annotation.read(input);
        }
        return new RuntimeInvisibleAnnotationsAttribute(annotations);
    }
}
