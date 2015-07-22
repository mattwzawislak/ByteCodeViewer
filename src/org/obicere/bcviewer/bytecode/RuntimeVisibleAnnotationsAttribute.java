package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeVisibleAnnotationsAttribute implements Attribute {

    private final Annotation[] annotations;

    public RuntimeVisibleAnnotationsAttribute(final Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations(){
        return annotations;
    }

}
