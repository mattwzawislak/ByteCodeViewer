package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeInvisibleAnnotationsAttribute extends Attribute {

    private final Annotation[] annotations;

    public RuntimeInvisibleAnnotationsAttribute(final Annotation[] annotations){
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations(){
        return annotations;
    }

}
