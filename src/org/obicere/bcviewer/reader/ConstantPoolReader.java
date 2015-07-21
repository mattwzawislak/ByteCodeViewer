package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Constant;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantPoolReader implements Reader<ConstantPool> {

    private final ConstantReader constantReader = new ConstantReader();

    @Override

    public ConstantPool read(final IndexedDataInputStream input) throws IOException {

        final int constantPoolCount = input.readUnsignedShort();
        final Constant[] constantPool = new Constant[constantPoolCount];

        // index 0 is reserved for compiler usage
        for (int i = 1; i < constantPoolCount; i++) {
            final Constant next = constantReader.read(input);

            constantPool[i] = next;

            final byte tag = next.getTag();
            // "In retrospect, making 8-byte constants take two constant pool entries was a poor choice."
            //    ~ Someone who had a bit of sense

            // Why they did this still shocks me. Maybe I should do
            // something with the index, such as storing the high bytes...
            if(tag == ConstantReader.CONSTANT_LONG || i == ConstantReader.CONSTANT_DOUBLE){
                i++;
            }
        }

        return new ConstantPool(constantPool);
    }
}
