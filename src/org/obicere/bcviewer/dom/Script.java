package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public enum Script {

    // no position offset, full size
    BASELINE(0, 1),

    // shifted down 33% of full size
    // 60% of full size
    SUBSCRIPT(-0.33, 0.6),

    // shifted up 33% of full size
    // 60% of full size
    SUPERSCRIPT(0.33, 0.6);

    private final double position;

    private final double size;

    private Script(final double position, final double size) {
        this.position = position;
        this.size = size;
    }

    public double getPosition() {
        return position;
    }

    public double getSize() {
        return size;
    }

}
