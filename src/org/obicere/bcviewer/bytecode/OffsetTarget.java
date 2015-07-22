package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class OffsetTarget implements Target {

    private final int targetType;
    private final int offset;

    public OffsetTarget(final int targetType, final int offset){
        this.targetType = targetType;
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }

}
