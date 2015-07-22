package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.AppendFrame;
import org.obicere.bcviewer.bytecode.ChopFrame;
import org.obicere.bcviewer.bytecode.FullFrame;
import org.obicere.bcviewer.bytecode.SameFrame;
import org.obicere.bcviewer.bytecode.SameFrameExtended;
import org.obicere.bcviewer.bytecode.SameLocals1StackItemFrame;
import org.obicere.bcviewer.bytecode.SameLocals1StackItemFrameExtended;
import org.obicere.bcviewer.bytecode.StackMapFrame;
import org.obicere.bcviewer.bytecode.VerificationTypeInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class StackMapFrameReader implements Reader<StackMapFrame> {

    //JVMS (§4.7.4)
    private static final int MAXIMUM_SAME                              = 63;
    private static final int MAXIMUM_SAME_LOCALS_1_STACK_ITEM          = 127;
    private static final int MAXIMUM_UNUSED                            = 246;
    private static final int MAXIMUM_SAME_LOCALS_1_STACK_ITEM_EXTENDED = 247;
    private static final int MAXIMUM_CHOP                              = 250;
    private static final int MAXIMUM_SAME_EXTENDED                     = 251;
    private static final int MAXIMUM_APPEND                            = 254;
    private static final int MAXIMUM_FULL                              = 255;

    private final VerificationTypeInfoReader verificationTypeInfo = new VerificationTypeInfoReader();

    @Override
    public StackMapFrame read(final IndexedDataInputStream input) throws IOException {
        final int frameType = input.readUnsignedByte();
        if (frameType <= MAXIMUM_SAME) {
            return readSameFrame(frameType);
        } else if (frameType <= MAXIMUM_SAME_LOCALS_1_STACK_ITEM) {
            return readSameLocals1StackItemFrame(frameType, input);
        } else if (frameType <= MAXIMUM_UNUSED) {
            throw new ClassFormatError("reached a reserved frame type.");
        } else if (frameType <= MAXIMUM_SAME_LOCALS_1_STACK_ITEM_EXTENDED) {
            return readSameLocals1StackItemFrameExtended(frameType, input);
        } else if (frameType <= MAXIMUM_CHOP) {
            return readChopFrame(frameType, input);
        } else if (frameType <= MAXIMUM_SAME_EXTENDED) {
            return readSameFrameExtended(frameType, input);
        } else if (frameType <= MAXIMUM_APPEND) {
            return readAppendFrame(frameType, input);
        } else if (frameType <= MAXIMUM_FULL) {
            return readFullFrame(frameType, input);
        } else {
            throw new AssertionError("if reached, data input stream read byte failed.");
        }
    }

    private SameFrame readSameFrame(final int frameType) {
        return new SameFrame(frameType);
    }

    private SameLocals1StackItemFrame readSameLocals1StackItemFrame(final int frameType, final IndexedDataInputStream input) throws IOException {
        final VerificationTypeInfo info = verificationTypeInfo.read(input);
        return new SameLocals1StackItemFrame(frameType, info);
    }

    private SameLocals1StackItemFrameExtended readSameLocals1StackItemFrameExtended(final int frameType, final IndexedDataInputStream input) throws IOException {
        final int offsetDelta = input.readShort();
        final VerificationTypeInfo info = verificationTypeInfo.read(input);
        return new SameLocals1StackItemFrameExtended(frameType, offsetDelta, info);
    }

    private ChopFrame readChopFrame(final int frameType, final IndexedDataInputStream input) throws IOException {
        final int offsetDelta = input.readShort();
        return new ChopFrame(frameType, offsetDelta);
    }

    private SameFrameExtended readSameFrameExtended(final int frameType, final IndexedDataInputStream input) throws IOException {
        final int offsetDelta = input.readShort();
        return new SameFrameExtended(frameType, offsetDelta);
    }

    private AppendFrame readAppendFrame(final int frameType, final IndexedDataInputStream input) throws IOException {
        final int offsetDelta = input.readShort();
        final int numberOfLocals = frameType - 251; //JVMS (§4.7.4)
        final VerificationTypeInfo[] locals = new VerificationTypeInfo[numberOfLocals];
        for(int i = 0; i < numberOfLocals; i++){
            locals[i] = verificationTypeInfo.read(input);
        }
        return new AppendFrame(frameType, offsetDelta, locals);
    }

    private FullFrame readFullFrame(final int frameType, final IndexedDataInputStream input) throws IOException {
        final int offsetDelta = input.readShort();

        final int numberOfLocals = input.readUnsignedShort();
        final VerificationTypeInfo[] locals = new VerificationTypeInfo[numberOfLocals];
        for(int i = 0; i < numberOfLocals; i++){
            locals[i] = verificationTypeInfo.read(input);
        }

        final int numberOfStackItems = input.readUnsignedShort();
        final VerificationTypeInfo[] stack = new VerificationTypeInfo[numberOfStackItems];
        for(int i = 0; i < numberOfStackItems; i++){
            stack[i] = verificationTypeInfo.read(input);
        }
        return new FullFrame(frameType, offsetDelta, locals, stack);
    }
}
