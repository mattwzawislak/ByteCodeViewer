package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public enum Script {

    BASELINE(0.33f, 1),

    SUBSCRIPT(0.33f, 0.6f),

    SUPERSCRIPT(1.33f, 0.6f);

    private final float position;

    private final float size;

    /**
     * Creates a new type of script.
     *
     * @param position The approximation used for the baseline.
     * @param size     The size of the script, to scale fonts.
     */
    private Script(final float position, final float size) {
        this.position = position;
        this.size = size;
    }

    public float getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }

}
