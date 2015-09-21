package org.obicere.bcviewer.dom;


import org.obicere.bcviewer.dom.attributes.CommentAttributes;
import org.obicere.bcviewer.dom.attributes.KeywordAttributes;
import org.obicere.bcviewer.dom.attributes.NumberAttributes;
import org.obicere.bcviewer.dom.attributes.PlainAttributes;
import org.obicere.bcviewer.dom.attributes.StringAttributes;

/**
 */
public class AttributesResourcePool extends ResourcePool<ResourceAttributes> {

    public static final String ATTRIBUTES_PLAIN  = "plain";

    public static final String ATTRIBUTES_KEYWORD = "keyword";

    public static final String ATTRIBUTES_NUMBER = "number";

    public static final String ATTRIBUTES_STRING = "string";

    public static final String ATTRIBUTES_COMMENT = "comment";

    public AttributesResourcePool() {
        safeAdd(ATTRIBUTES_KEYWORD, new KeywordAttributes());
        safeAdd(ATTRIBUTES_NUMBER, new NumberAttributes());
        safeAdd(ATTRIBUTES_STRING, new StringAttributes());
        safeAdd(ATTRIBUTES_PLAIN, new PlainAttributes());
        safeAdd(ATTRIBUTES_COMMENT, new CommentAttributes());
    }

    void updateFonts(final FontResourcePool resourcePool) {
        for (final ResourceAttributes attributes : getResources()) {
            attributes.updateFont(resourcePool);
        }
    }

    void updateColors(final ColorResourcePool resourcePool) {
        for (final ResourceAttributes attributes : getResources()) {
            attributes.updateColor(resourcePool);
        }
    }
}
