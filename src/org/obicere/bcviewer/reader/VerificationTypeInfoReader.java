package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.VerificationTypeInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class VerificationTypeInfoReader implements Reader<VerificationTypeInfo> {

    public static final int ITEM_TOP                = 0;
    public static final int ITEM_INTEGER            = 1;
    public static final int ITEM_FLOAT              = 2;
    public static final int ITEM_DOUBLE             = 3;
    public static final int ITEM_LONG               = 4;
    public static final int ITEM_NULL               = 5;
    public static final int ITEM_UNINITIALIZED_THIS = 6;
    public static final int ITEM_OBJECT             = 7;
    public static final int ITEM_UNINITIALIZED      = 8;

    @Override
    public VerificationTypeInfo read(final IndexedDataInputStream input) throws IOException {
        final int tag = input.readUnsignedByte();
        if (tag == ITEM_OBJECT || tag == ITEM_UNINITIALIZED) {
            return new VerificationTypeInfo(tag, input.readUnsignedShort());
        } else {
            return new VerificationTypeInfo(tag);
        }
    }
}
