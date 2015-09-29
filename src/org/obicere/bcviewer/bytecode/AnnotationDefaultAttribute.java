package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class AnnotationDefaultAttribute extends Attribute {

    private final ElementValue defaultValue;

    public AnnotationDefaultAttribute(final ElementValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ElementValue getDefaultValue(){
        return defaultValue;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        // modelling not held here
    }
}
