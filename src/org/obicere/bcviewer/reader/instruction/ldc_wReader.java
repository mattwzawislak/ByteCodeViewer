package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ldc_w;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ldc_wReader implements Reader<ldc_w> {

    @Override
    public ldc_w read(final IndexedDataInputStream input) throws IOException {
        return new ldc_w(input.readUnsignedByte(), input.readUnsignedByte());
    }
}