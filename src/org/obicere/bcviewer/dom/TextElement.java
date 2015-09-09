package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class TextElement extends Element {

    private String text;

    private int leftPad  = 0;
    private int rightPad = 0;

    private TextAttributes attributes = new TextAttributes();

    private final TextView view = new TextView(this);

    public TextElement(final String name) {
        super(name);
    }

    public TextElement(final String name, final String text) {
        super(name);
        // delegate to setter to handle null-case
        setText(text);
    }

    public void setAttributes(final TextAttributes attributes) {
        this.attributes = attributes;
    }

    public String getDisplayText() {
        final PaddingCache cache = PaddingCache.getPaddingCache();
        return cache.getPadding(getCumulativeLeftPad()) + getText() + cache.getPadding(getCumulativeRightPad());
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
    }

    public void setLeftPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.leftPad = pad;
    }

    public void setRightPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.rightPad = pad;
    }

    public int getCumulativeLeftPad() {
        Element parent = getParent();
        int sum = leftPad;
        while (parent != null) {
            if (parent instanceof TextElement) {
                sum += ((TextElement) parent).getLeftPad();
            }
            parent = parent.getParent();
        }
        return sum;
    }

    public int getCumulativeRightPad() {
        Element parent = getParent();
        int sum = rightPad;
        while (parent != null) {
            if (parent instanceof TextElement) {
                sum += ((TextElement) parent).getRightPad();
            }
            parent = parent.getParent();
        }
        return sum;
    }

    public int getLeftPad() {
        return leftPad;
    }

    public int getRightPad() {
        return rightPad;
    }

    public TextAttributes getAttributes() {
        return attributes;
    }

    public Caret getCaret(final int x, final int y) {
        final int index = getView().getCaretIndex(x, y);
        if (index == -1) {
            return null;
        } else {
            return new Caret(this, index);
        }
    }

    @Override
    public TextView getView() {
        return view;
    }

    @Override
    public void writeSelf(final DocumentContent content) {
        content.write(getDisplayText());
    }
}
