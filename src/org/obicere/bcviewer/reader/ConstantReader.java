package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Constant;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.MultiReader;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantReader extends MultiReader<Integer, Constant> {

    public static final int CONSTANT_UTF8                 = 1;
    public static final int CONSTANT_INTEGER              = 3;
    public static final int CONSTANT_FLOAT                = 4;
    public static final int CONSTANT_LONG                 = 5;
    public static final int CONSTANT_DOUBLE               = 6;
    public static final int CONSTANT_CLASS                = 7;
    public static final int CONSTANT_STRING               = 8;
    public static final int CONSTANT_FIELD_REF            = 9;
    public static final int CONSTANT_METHOD_REF           = 10;
    public static final int CONSTANT_INTERFACE_METHOD_REF = 11;
    public static final int CONSTANT_NAME_AND_TYPE        = 12;
    public static final int CONSTANT_METHOD_HANDLE        = 15;
    public static final int CONSTANT_METHOD_TYPE          = 16;
    public static final int CONSTANT_INVOKE_DYNAMIC       = 18;

    public ConstantReader() {
        add(CONSTANT_UTF8, new ConstantUtf8Reader());
        add(CONSTANT_INTEGER, new ConstantIntegerReader());
        add(CONSTANT_FLOAT, new ConstantFloatReader());
        add(CONSTANT_LONG, new ConstantLongReader());
        add(CONSTANT_DOUBLE, new ConstantDoubleReader());
        add(CONSTANT_CLASS, new ConstantClassReader());
        add(CONSTANT_STRING, new ConstantStringReader());
        add(CONSTANT_FIELD_REF, new ConstantFieldRefReader());
        add(CONSTANT_METHOD_REF, new ConstantMethodRefReader());
        add(CONSTANT_INTERFACE_METHOD_REF, new ConstantInterfaceMethodRefReader());
        add(CONSTANT_NAME_AND_TYPE, new ConstantNameAndTypeReader());
        add(CONSTANT_METHOD_HANDLE, new ConstantMethodHandleReader());
        add(CONSTANT_METHOD_TYPE, new ConstantMethodTypeReader());
        add(CONSTANT_INVOKE_DYNAMIC, new ConstantInvokeDynamicReader());
    }

    @Override
    public Constant read(final IndexedDataInputStream input) throws IOException {
        final int next = input.readUnsignedByte();
        final int start = input.getLogicalIndex();
        final Reader<? extends Constant> reader = get(next);
        if (reader == null) {
            throw new ClassFormatError("invalid constant tag: " + next);
        }
        final Constant constant = reader.read(input);
        final int end = input.getLogicalIndex();
        constant.setBounds(start, end);
        return constant;
    }
}
