package org.obicere.bcviewer.dom;

import java.awt.Font;

/**
 */
public class FontResourcePool extends ResourcePool<Font> {

    public static final String FONT_SUBSCRIPT_PLAIN       = "subscript.plain";
    public static final String FONT_SUBSCRIPT_BOLD        = "subscript.bold";
    public static final String FONT_SUBSCRIPT_ITALIC      = "subscript.italic";
    public static final String FONT_SUBSCRIPT_BOLD_ITALIC = "subscript.bolditalic";

    public static final String FONT_BASELINE_PLAIN       = "baseline.plain";
    public static final String FONT_BASELINE_BOLD        = "baseline.bold";
    public static final String FONT_BASELINE_ITALIC      = "baseline.italic";
    public static final String FONT_BASELINE_BOLD_ITALIC = "baseline.bolditalic";

    public static final String FONT_SUPERSCRIPT_PLAIN       = "superscript.plain";
    public static final String FONT_SUPERSCRIPT_BOLD        = "superscript.bold";
    public static final String FONT_SUPERSCRIPT_ITALIC      = "superscript.italic";
    public static final String FONT_SUPERSCRIPT_BOLD_ITALIC = "superscript.bolditalic";

    private String baseFontName;

    private final DocumentBuilder builder;

    public FontResourcePool(final DocumentBuilder builder) {
        this.builder = builder;
    }

    public Font get(final Script script, final boolean bold, final boolean italic) {
        final String name;
        switch (script) {
            case BASELINE:
                name = "baseline";
                break;
            case SUPERSCRIPT:
                name = "superscript";
                break;
            case SUBSCRIPT:
                name = "subscript";
                break;
            default:
                throw new IllegalStateException();
        }
        final String style;
        if (bold) {
            style = italic ? "bolditalic" : "bold";
        } else {
            style = italic ? "italic" : "plain";
        }
        return get(name + "." + style);
    }

    @Override
    public void add(final String name, final Font font){
        super.add(name, font);
        builder.notifyFontChange();
    }

    public void setBaseFont(final String name, final int size) {
        if (name == null) {
            throw new NullPointerException("font name cannot be null.");
        }

        if (name.equals(baseFontName)) {
            return;
        }

        this.baseFontName = name;

        loadBaseline(name, size);
        loadSubscript(name, size);
        loadSuperscript(name, size);

        builder.notifyFontChange();
    }

    public String getBaseFontName() {
        return baseFontName;
    }

    private void loadSuperscript(final String name, final int size) {

        final int fixedSize = (int) (size * Script.SUPERSCRIPT.getSize());

        safeAdd(FONT_SUPERSCRIPT_PLAIN, new Font(name, Font.PLAIN, fixedSize));
        safeAdd(FONT_SUPERSCRIPT_BOLD, new Font(name, Font.BOLD, fixedSize));
        safeAdd(FONT_SUPERSCRIPT_ITALIC, new Font(name, Font.ITALIC, fixedSize));
        safeAdd(FONT_SUPERSCRIPT_BOLD_ITALIC, new Font(name, Font.BOLD | Font.ITALIC, fixedSize));
    }

    private void loadSubscript(final String name, final int size) {
        final int fixedSize = (int) (size * Script.SUBSCRIPT.getSize());

        safeAdd(FONT_SUBSCRIPT_PLAIN, new Font(name, Font.PLAIN, fixedSize));
        safeAdd(FONT_SUBSCRIPT_BOLD, new Font(name, Font.BOLD, fixedSize));
        safeAdd(FONT_SUBSCRIPT_ITALIC, new Font(name, Font.ITALIC, fixedSize));
        safeAdd(FONT_SUBSCRIPT_BOLD_ITALIC, new Font(name, Font.BOLD | Font.ITALIC, fixedSize));
    }

    private void loadBaseline(final String name, final int size) {

        safeAdd(FONT_BASELINE_PLAIN, new Font(name, Font.PLAIN, size));
        safeAdd(FONT_BASELINE_BOLD, new Font(name, Font.BOLD, size));
        safeAdd(FONT_BASELINE_ITALIC, new Font(name, Font.ITALIC, size));
        safeAdd(FONT_BASELINE_BOLD_ITALIC, new Font(name, Font.BOLD | Font.ITALIC, size));
    }

}
