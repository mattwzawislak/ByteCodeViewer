package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeInvisibleParameterAnnotationsAttribute extends Attribute {

    private final Annotation[][] parameterAnnotations;

    public RuntimeInvisibleParameterAnnotationsAttribute(final int attributeNameIndex, final int attributeLength, final Annotation[][] parameterAnnotations){
        super(attributeNameIndex, attributeLength);

        if(parameterAnnotations == null){
            throw new NullPointerException("parameter annotations not defined.");
        }

        for(final Annotation[] annotations : parameterAnnotations) {
            if(annotations == null){
                throw new NullPointerException("annotations not defined.");
            }
        }
        this.parameterAnnotations = parameterAnnotations;
    }

    public Annotation[][] getParameterAnnotations(){
        return parameterAnnotations;
    }

    public Annotation[] getAnnotations(final int parameter){
        return parameterAnnotations[parameter];
    }

}
