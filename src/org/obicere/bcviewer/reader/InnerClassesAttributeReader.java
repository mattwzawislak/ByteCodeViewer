package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.InnerClass;
import org.obicere.bcviewer.bytecode.InnerClassesAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class InnerClassesAttributeReader implements Reader<InnerClassesAttribute> {

    private final InnerClassReader innerClass = new InnerClassReader();

    @Override
    public InnerClassesAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numberOfClasses = input.readUnsignedShort();
        final InnerClass[] classes = new InnerClass[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            final int startIndex = input.getLogicalIndex();
            final InnerClass cls = innerClass.read(input);
            final int endIndex = input.getLogicalIndex();
            cls.setBounds(startIndex, endIndex);

            classes[i] = cls;

        }
        return new InnerClassesAttribute(classes);
    }
}
