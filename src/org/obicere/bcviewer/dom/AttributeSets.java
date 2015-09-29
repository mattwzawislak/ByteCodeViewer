package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.attributes.AnnotationAttributeSet;
import org.obicere.bcviewer.dom.attributes.CommentAttributeSet;
import org.obicere.bcviewer.dom.attributes.KeywordAttributeSet;
import org.obicere.bcviewer.dom.attributes.NumberAttributeSet;
import org.obicere.bcviewer.dom.attributes.PlainAttributeSet;
import org.obicere.bcviewer.dom.attributes.StringAttributeSet;
import org.obicere.bcviewer.dom.attributes.TypeAttributeSet;

import javax.swing.text.AttributeSet;
import java.util.HashMap;

/**
 */
public class AttributeSets {

    public static final String ANNOTATION = "annotation";

    public static final String COMMENT = "comment";

    public static final String KEYWORD = "keyword";

    public static final String NUMBER = "number";

    public static final String PLAIN = "plain";

    public static final String STRING = "string";

    public static final String TYPE = "type";

    private HashMap<String, AttributeSet> attributes = new HashMap<>();

    public AttributeSets() {
        attributes.put(ANNOTATION, new AnnotationAttributeSet());
        attributes.put(COMMENT, new CommentAttributeSet());
        attributes.put(KEYWORD, new KeywordAttributeSet());
        attributes.put(NUMBER, new NumberAttributeSet());
        attributes.put(PLAIN, new PlainAttributeSet());
        attributes.put(STRING, new StringAttributeSet());
        attributes.put(TYPE, new TypeAttributeSet());
    }

    public AttributeSet getAttributeSet(final String attributeSetName) {
        return attributes.get(attributeSetName);
    }

    public AttributeSet getAttributeSet(final String attributeSetName, final AttributeSet defaultValue) {
        return attributes.getOrDefault(attributeSetName, defaultValue);
    }
}
