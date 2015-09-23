package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.ElementValuePair;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class AnnotationReader implements Reader<Annotation> {

    private final ElementValueReader elementValue;

    public AnnotationReader() {
        this.elementValue = new ElementValueReader(this);
    }

    @Override
    public Annotation read(final IndexedDataInputStream input) throws IOException {
        final int typeIndex = input.readUnsignedShort();
        final int numElementValuePairs = input.readUnsignedShort();
        final ElementValuePair[] pairs = new ElementValuePair[numElementValuePairs];

        for (int i = 0; i < numElementValuePairs; i++) {
            pairs[i] = new ElementValuePair(input.readUnsignedShort(), elementValue.read(input));
        }
        return new Annotation(typeIndex, pairs);
    }
}
