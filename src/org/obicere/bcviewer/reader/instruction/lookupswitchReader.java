package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lookupswitch;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lookupswitchReader implements Reader<lookupswitch> {

    @Override
    public lookupswitch read(final IndexedDataInputStream input) throws IOException {
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
        final int npairs1 = input.readUnsignedByte();
        final int npairs2 = input.readUnsignedByte();
        final int npairs3 = input.readUnsignedByte();
        final int npairs4 = input.readUnsignedByte();

        final int npairs = (npairs1 << 24) | (npairs2 << 16) | (npairs3 << 8) | npairs4;

        final int[][] pairs = new int[npairs][2];
        for(int i = 0; i < npairs; i++){
            pairs[i][0] = input.readInt();
            pairs[i][1] = input.readInt();
        }
        return new lookupswitch(defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, npairs1, npairs2, npairs3, npairs4, pairs);
    }
}