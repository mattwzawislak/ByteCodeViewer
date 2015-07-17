package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_icmplt;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_icmpltReader implements Reader<if_icmplt> {

    @Override
    public if_icmplt read(final IndexedDataInputStream input) throws IOException {
        return new if_icmplt(input.readUnsignedByte(), input.readUnsignedByte());
    }
}