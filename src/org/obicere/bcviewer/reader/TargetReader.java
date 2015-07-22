package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Target;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TargetReader implements Reader<Target> {

    private final TypeParameterTargetReader         typeParameterTarget         = new TypeParameterTargetReader();
    private final SuperTypeTargetReader             superTypeTarget             = new SuperTypeTargetReader();
    private final TypeParameterBoundTargetReader    typeParameterBoundTarget    = new TypeParameterBoundTargetReader();
    private final EmptyTargetReader                 emptyTarget                 = new EmptyTargetReader();
    private final MethodFormalParameterTargetReader methodFormalParameterTarget = new MethodFormalParameterTargetReader();
    private final ThrowsTargetReader                throwsTarget                = new ThrowsTargetReader();
    private final LocalVarTargetReader              localVarTarget              = new LocalVarTargetReader();
    private final CatchTargetReader                 catchTarget                 = new CatchTargetReader();
    private final OffsetTargetReader                offsetTarget                = new OffsetTargetReader();
    private final TypeArgumentTargetReader          typeArgumentTarget          = new TypeArgumentTargetReader();

    @Override
    public Target read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        switch (targetType){

            // type 1 values
            case 0x0:
            case 0x1:
                return typeParameterTarget.read(input);
            case 0x10:
                return superTypeTarget.read(input);
            case 0x11:
            case 0x12:
                return typeParameterBoundTarget.read(input);
            case 0x13:
            case 0x14:
            case 0x15:
                return emptyTarget.read(input);
            case 0x16:
                return methodFormalParameterTarget.read(input);
            case 0x17:
                return throwsTarget.read(input);

            // type 2 values
            case 0x40:
            case 0x41:
                return localVarTarget.read(input);
            case 0x42:
                return catchTarget.read(input);
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46:
                return offsetTarget.read(input);
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4A:
            case 0x4B:
                return typeArgumentTarget.read(input);
            default:
                throw new ClassFormatError("invalid target type value: " + targetType);

        }
    }
}
