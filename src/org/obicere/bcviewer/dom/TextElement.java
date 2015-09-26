package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class TextElement extends Element {

    private String text;

    private int leftPad  = 0;
    private int rightPad = 0;

    private int highlightStart = 0;
    private int highlightEnd   = 0;

    private boolean highlight = false;

    private Attributes attributes = new Attributes();

    private final TextView view = new TextView(this);

    public TextElement(final String name) {
        this(name, null);
    }

    public TextElement(final String name, final String text) {
        super(name);
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
    }

    public void setAttributes(final Attributes attributes) {
        this.attributes = attributes;
    }

    public String getDisplayText() {
        final PaddingCache cache = PaddingCache.getPaddingCache();
        return cache.getPadding(getLeftPad()) + getText() + cache.getPadding(getRightPad());
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
        view.dispose();
        clearHighlight();
    }

    public void setLeftPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.leftPad = pad;
        view.dispose();
        clearHighlight();
    }

    public void setRightPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.rightPad = pad;
        view.dispose();
        clearHighlight();
    }

    public int getLeftPad() {
        return leftPad;
    }

    public int getLeftPadIndex() {
        return leftPad;
    }

    public int getRightPad() {
        return rightPad;
    }

    public int getRightPadIndex() {
        // getDisplayLength() - rightPad
        return leftPad + text.length();
    }

    public int getDisplayLength() {
        return leftPad + text.length() + rightPad;
    }

    public boolean isHighlighted() {
        return highlight;
    }

    public boolean isHighlightClipped() {
        // if not highlighting, no clip
        if (!highlight) {
            return false;
        }
        // if the highlight start clips the text
        if (leftPad < highlightStart && highlightStart < getRightPadIndex()) {
            return true;
        }
        // or if the highlight end clips the text
        return leftPad < highlightEnd && highlightEnd < getRightPadIndex();
    }

    public int getHighlightStart() {
        return highlightStart;
    }

    public int getHighlightEnd() {
        return highlightEnd;
    }

    public void clearHighlight() {
        highlightStart = 0;
        highlightEnd = 0;
        highlight = false;
    }

    public void setHighlightText() {
        setHighlight(leftPad, leftPad + text.length());
    }

    public void setHighlightDisplayText() {
        setHighlight(0, getDisplayLength());
    }

    public void setHighlight(final int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("cannot have start of highlight be < 0.");
        }
        if (getDisplayLength() < end) {
            throw new IllegalArgumentException("end of highlight must be <= display length.");
        }
        if (end < start) {
            throw new IllegalArgumentException("start must be <= end");
        }
        highlightStart = start;
        highlightEnd = end;
        if (start != end) {
            // only highlight when there is some text to highlight
            highlight = true;
        }
    }

    public Attributes getAttributes() {
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
        content.write(getText());
    }
}
