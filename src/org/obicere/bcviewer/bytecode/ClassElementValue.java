package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ClassElementValue extends ElementValue {

    private static final int TAG = 'B';

    private final int classInfoIndex;

    public ClassElementValue(final int classInfoIndex) {
        super(TAG);
        this.classInfoIndex = classInfoIndex;
    }

    public int getClassInfoIndex() {
        return classInfoIndex;
    }
}
