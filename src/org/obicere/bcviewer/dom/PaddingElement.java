package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class PaddingElement extends TextElement {

    public static final int PAD_SPACE = 0x0;
    public static final int PAD_TAB   = 0x1;

    private final int type;
    private final int count;

    public PaddingElement(final String name, final int type) {
        this(name, type, 1);
    }

    public PaddingElement(final String name, final int type, final int count) {
        super(name);
        this.type = type;
        this.count = count;

        super.setText(getPadding(type, count));
    }

    @Override
    public void setText(final String text) {
        // cannot set text of padding element
    }

    public int getCount() {
        return count;
    }

    public int getType() {
        return type;
    }

    private String getPadding(final int type, final int count) {
        final char[] chars = new char[count];
        final char fill;
        switch (type) {
            case PAD_SPACE:
                fill = ' ';
                break;
            case PAD_TAB:
                fill = '\t';
                break;
            default:
                throw new AssertionError("invalid state for padding element.");
        }
        for (int i = 0; i < count; i++) {
            chars[i] = fill;
        }
        return new String(chars);
    }

    @Override
    public boolean isPrimary() {
        return false;
    }
}
