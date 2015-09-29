package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : annotations) {
            annotation.model(builder, parent);
        }
    }
}
