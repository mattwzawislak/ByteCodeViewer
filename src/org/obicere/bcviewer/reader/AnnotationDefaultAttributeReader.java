package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.AnnotationDefaultAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class AnnotationDefaultAttributeReader implements Reader<AnnotationDefaultAttribute> {

    private final ElementValueReader elementValue;

    public AnnotationDefaultAttributeReader(final ElementValueReader elementValue) {
        this.elementValue = elementValue;
    }

    @Override
    public AnnotationDefaultAttribute read(final IndexedDataInputStream input) throws IOException {
        return new AnnotationDefaultAttribute(elementValue.read(input));
    }
}
