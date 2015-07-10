package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class AnnotationDefaultAttribute extends Attribute {

    private final ElementValue defaultValue;

    public AnnotationDefaultAttribute(final int attributeNameIndex, final int attributeLength, final ElementValue defaultValue) {
        super(attributeNameIndex, attributeLength);
        this.defaultValue = defaultValue;
    }

    public ElementValue getDefaultValue(){
        return defaultValue;
    }

}
