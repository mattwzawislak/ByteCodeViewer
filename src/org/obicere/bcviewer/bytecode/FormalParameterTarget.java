package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class FormalParameterTarget implements Target {

    private final int formalParameterIndex;

    public FormalParameterTarget(final int formalParameterIndex) {
        this.formalParameterIndex = formalParameterIndex;
    }

    public int getFormalParameterIndex() {
        return formalParameterIndex;
    }

}
