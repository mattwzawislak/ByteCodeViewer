package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.tableswitch;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class tableswitchReader implements Reader<tableswitch> {

    @Override
    public tableswitch read(final IndexedDataInputStream input) throws IOException {
        // how many bytes to skip to get to next index divisible by 4
        final int skip = (4 - (input.getIndex() % 4)) % 4;
        // let n = index % 4
        // n = 0, skip = 0
        // n = 1, skip = 3
        // n = 2, skip = 2
        // n = 3, skip = 1
        for(int i = 0; i < skip; i++){
            input.readByte();
        }
        final int defaultbyte1 = input.readUnsignedByte();
        final int defaultbyte2 = input.readUnsignedByte();
        final int defaultbyte3 = input.readUnsignedByte();
        final int defaultbyte4 = input.readUnsignedByte();
        final int lowbyte1 = input.readUnsignedByte();
        final int lowbyte2 = input.readUnsignedByte();
        final int lowbyte3 = input.readUnsignedByte();
        final int lowbyte4 = input.readUnsignedByte();
        final int highbyte1 = input.readUnsignedByte();
        final int highbyte2 = input.readUnsignedByte();
        final int highbyte3 = input.readUnsignedByte();
        final int highbyte4 = input.readUnsignedByte();

        final int low = (lowbyte1 << 24) | (lowbyte2 << 16) | (lowbyte3 << 8) | lowbyte4;
        final int high = (highbyte1 << 24) | (highbyte2 << 16) | (highbyte3 << 8) | highbyte4;
        final int jumps = high - low + 1;

        final int[] offsets = new int[jumps];
        for(int i = 0; i < jumps; i++){
            offsets[i] = input.readInt();
        }
        return new tableswitch(defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3, highbyte4, offsets);
    }
}