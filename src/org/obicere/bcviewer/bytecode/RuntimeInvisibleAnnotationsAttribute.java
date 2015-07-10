package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeInvisibleAnnotationsAttribute extends Attribute {

    private final Annotation[] annotations;

    public RuntimeInvisibleAnnotationsAttribute(final int attributeNameIndex, final int attributeLength, final Annotation[] annotations){
        super(attributeNameIndex, attributeLength);
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations(){
        return annotations;
    }

}
