package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ElementValuePair extends BytecodeElement{

    private final int          elementNameIndex;
    private final ElementValue value;

    public ElementValuePair(final int elementNameIndex, final ElementValue value) {

        if (value == null) {
            throw new NullPointerException("value is not defined.");
        }

        this.elementNameIndex = elementNameIndex;
        this.value = value;
    }

    public int getElementNameIndex() {
        return elementNameIndex;
    }

    public ElementValue getValue() {
        return value;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent){
        final ConstantPool constantPool = builder.getConstantPool();
        builder.addPlain(constantPool.getAsString(elementNameIndex));
        builder.addPlain(" = ");
        value.model(builder, parent);
    }

}
