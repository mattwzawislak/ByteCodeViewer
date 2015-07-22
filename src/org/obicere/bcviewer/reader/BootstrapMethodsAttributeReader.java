package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.BootstrapMethod;
import org.obicere.bcviewer.bytecode.BootstrapMethodsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class BootstrapMethodsAttributeReader implements Reader<BootstrapMethodsAttribute> {

    private final BootstrapMethodReader bootstrapMethod = new BootstrapMethodReader();

    @Override
    public BootstrapMethodsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numBootstrapMethods = input.readUnsignedShort();
        final BootstrapMethod[] bootstrapMethods = new BootstrapMethod[numBootstrapMethods];
        for (int i = 0; i < numBootstrapMethods; i++) {
            bootstrapMethods[i] = bootstrapMethod.read(input);
        }
        return new BootstrapMethodsAttribute(bootstrapMethods);
    }
}
