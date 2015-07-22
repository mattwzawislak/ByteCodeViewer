package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeInvisibleParameterAnnotationsAttribute implements Attribute {

    private final Annotation[][] parameterAnnotations;

    public RuntimeInvisibleParameterAnnotationsAttribute(final Annotation[][] parameterAnnotations){

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
