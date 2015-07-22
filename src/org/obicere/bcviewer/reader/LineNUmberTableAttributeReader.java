package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LineNumber;
import org.obicere.bcviewer.bytecode.LineNumberTableAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LineNumberTableAttributeReader implements Reader<LineNumberTableAttribute> {

    private final LineNumberReader lineNumber = new LineNumberReader();

    @Override
    public LineNumberTableAttribute read(final IndexedDataInputStream input) throws IOException {
        final int lineNumberTableLength = input.readUnsignedShort();
        final LineNumber[] lineNumberTable = new LineNumber[lineNumberTableLength];

        for(int i = 0; i < lineNumberTableLength; i++){
            lineNumberTable[i] = lineNumber.read(input);
        }
        return new LineNumberTableAttribute(lineNumberTable);
    }
}
