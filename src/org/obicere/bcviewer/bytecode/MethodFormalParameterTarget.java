package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class MethodFormalParameterTarget implements Target {

    private final int targetType;
    private final int formalParameterIndex;

    public MethodFormalParameterTarget(final int targetType, final int formalParameterIndex) {
        this.targetType = targetType;
        this.formalParameterIndex = formalParameterIndex;
    }

    public int getFormalParameterIndex() {
        return formalParameterIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}
