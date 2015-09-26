package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.VerificationTypeInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.MultiReader;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class VerificationTypeInfoReader extends MultiReader<Integer, VerificationTypeInfo> {

    public static final int ITEM_TOP                = 0;
    public static final int ITEM_INTEGER            = 1;
    public static final int ITEM_FLOAT              = 2;
    public static final int ITEM_DOUBLE             = 3;
    public static final int ITEM_LONG               = 4;
    public static final int ITEM_NULL               = 5;
    public static final int ITEM_UNINITIALIZED_THIS = 6;
    public static final int ITEM_OBJECT             = 7;
    public static final int ITEM_UNINITIALIZED      = 8;

    public VerificationTypeInfoReader() {
        add(ITEM_TOP, new TopVariableInfoReader());
        add(ITEM_INTEGER, new IntegerVariableInfoReader());
        add(ITEM_FLOAT, new FloatVariableInfoReader());
        add(ITEM_DOUBLE, new DoubleVariableInfoReader());
        add(ITEM_LONG, new LongVariableInfoReader());
        add(ITEM_NULL, new NullVariableInfoReader());
        add(ITEM_UNINITIALIZED_THIS, new UninitializedThisVariableInfoReader());
        add(ITEM_OBJECT, new ObjectVariableInfoReader());
        add(ITEM_UNINITIALIZED, new UninitializedVariableInfoReader());
    }

    @Override
    public VerificationTypeInfo read(final IndexedDataInputStream input) throws IOException {
        final int start = input.getOffsetIndex();
        final int type = input.readUnsignedByte();
        input.stepBack(1);
        final Reader<? extends VerificationTypeInfo> reader = get(type);

        if(reader == null){
            throw new IllegalArgumentException("no reader for input: " + type);
        }

        final VerificationTypeInfo info = reader.read(input);
        final int end = input.getOffsetIndex();
        info.setBounds(start, end);
        return info;
    }
}
