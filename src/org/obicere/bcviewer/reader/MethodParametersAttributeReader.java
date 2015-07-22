package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.MethodParametersAttribute;
import org.obicere.bcviewer.bytecode.Parameter;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class MethodParametersAttributeReader implements Reader<MethodParametersAttribute> {

    private final ParameterReader parameter = new ParameterReader();

    @Override
    public MethodParametersAttribute read(final IndexedDataInputStream input) throws IOException {
        final int parametersCount = input.readUnsignedByte();
        final Parameter[] parameters = new Parameter[parametersCount];
        for(int i = 0; i < parametersCount; i++){
            parameters[i] = parameter.read(input);
        }
        return new MethodParametersAttribute(parameters);
    }
}
