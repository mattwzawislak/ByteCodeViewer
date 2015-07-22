package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.RuntimeInvisibleParameterAnnotationsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeInvisibleParameterAnnotationsAttributeReader implements Reader<RuntimeInvisibleParameterAnnotationsAttribute> {

    private final AnnotationReader annotation;

    public RuntimeInvisibleParameterAnnotationsAttributeReader(final AnnotationReader annotation) {
        this.annotation = annotation;
    }

    @Override
    public RuntimeInvisibleParameterAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numParameters = input.readUnsignedShort();
        final Annotation[][] annotations = new Annotation[numParameters][];

        for (int i = 0; i < numParameters; i++) {
            final int numAnnotations = input.readUnsignedShort();
            annotations[i] = new Annotation[numAnnotations];
            for(int j = 0; j < numAnnotations; j++){
                annotations[i][j] = annotation.read(input);
            }
        }
        return new RuntimeInvisibleParameterAnnotationsAttribute(annotations);
    }
}
