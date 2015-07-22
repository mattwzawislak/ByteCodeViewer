package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.AnnotationElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class AnnotationElementValueReader implements Reader<AnnotationElementValue> {

    private final AnnotationReader annotationReader;

    public AnnotationElementValueReader(final AnnotationReader annotationReader) {
        this.annotationReader = annotationReader;
    }

    @Override
    public AnnotationElementValue read(final IndexedDataInputStream input) throws IOException {
        return new AnnotationElementValue(annotationReader.read(input));
    }
}
