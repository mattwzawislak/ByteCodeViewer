package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class BytecodeElement {

    private int start;
    private int end;

    public final int getStart() {
        return start;
    }

    public final int getEnd() {
        return end;
    }

    public final void setStart(final int start) {
        if (start < 0 || start >= end) {
            throw new IllegalArgumentException("invalid start value: " + start);
        }
        this.start = start;
    }

    public final void setEnd(final int end) {
        if (end <= start) {
            // since start >= 0
            // implicitly end > 0
            throw new IllegalArgumentException("invalid end value: " + start);
        }
        this.end = end;
    }

    public final int getLength(){
        return end - start;
    }

}
