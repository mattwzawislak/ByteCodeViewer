package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeVisibleAnnotationsAttributeReader implements Reader<RuntimeVisibleAnnotationsAttribute> {

    private final AnnotationReader annotation;

    public RuntimeVisibleAnnotationsAttributeReader(final AnnotationReader annotation) {
        this.annotation = annotation;
    }

    @Override
    public RuntimeVisibleAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numAnnotations = input.readUnsignedShort();
        final Annotation[] annotations = new Annotation[numAnnotations];

        for (int i = 0; i < numAnnotations; i++) {
            annotations[i] = annotation.read(input);
        }
        return new RuntimeVisibleAnnotationsAttribute(annotations);
    }
}
