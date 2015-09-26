package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.TopVariableInfo;
import org.obicere.bcviewer.bytecode.VerificationTypeInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class TopVariableInfoReader implements Reader<VerificationTypeInfo> {

    @Override
    public VerificationTypeInfo read(final IndexedDataInputStream input) throws IOException {
        return new TopVariableInfo(input.readUnsignedByte());
    }
}
