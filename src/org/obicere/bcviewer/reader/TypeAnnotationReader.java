package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ElementValue;
import org.obicere.bcviewer.bytecode.ElementValuePair;
import org.obicere.bcviewer.bytecode.Target;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.bytecode.TypePath;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TypeAnnotationReader implements Reader<TypeAnnotation> {

    private final TargetReader target = new TargetReader();

    private final TypePathReader typePath = new TypePathReader();

    private final ElementValueReader elementValue;

    public TypeAnnotationReader(final ElementValueReader elementValue) {
        this.elementValue = elementValue;
    }

    @Override
    public TypeAnnotation read(final IndexedDataInputStream input) throws IOException {
        final Target targetInfo = target.read(input);
        final TypePath path = typePath.read(input);
        final int typeIndex = input.readUnsignedShort();
        final int numElementValuePairs = input.readUnsignedShort();

        final ElementValuePair[] elementValuePairs = new ElementValuePair[numElementValuePairs];
        for(int i = 0;i < numElementValuePairs; i++){
            final int elementNameIndex = input.readUnsignedShort();
            final ElementValue value = elementValue.read(input);

            elementValuePairs[i] = new ElementValuePair(elementNameIndex, value);
        }
        return new TypeAnnotation(targetInfo.getTargetType(), targetInfo, path, typeIndex, elementValuePairs);
    }
}
