package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class InnerClass {

    private final int innerClassInfoIndex;
    private final int outerClassInfoIndex;
    private final int innerNameIndex;
    private final int innerClassAccessFlags;

    public InnerClass(final int innerClassInfoIndex, final int outerClassInfoIndex, final int innerNameIndex, final int innerClassAccessFlags){
        this.innerClassInfoIndex = innerClassInfoIndex;
        this.outerClassInfoIndex = outerClassInfoIndex;
        this.innerNameIndex = innerNameIndex;
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public int getInnerClassInfoIndex(){
        return innerClassInfoIndex;
    }

    public int getOuterClassInfoIndex() {
        return outerClassInfoIndex;
    }

    public int getInnerNameIndex() {
        return innerNameIndex;
    }

    public int getInnerClassAccessFlags() {
        return innerClassAccessFlags;
    }
}
