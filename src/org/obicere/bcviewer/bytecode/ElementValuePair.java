package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;

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
    public void model(final DocumentBuilder builder, final Element parent){
        final ConstantPool constantPool = builder.getConstantPool();
        parent.add(new PlainElement("name", constantPool.getAsString(elementNameIndex), builder));

        final PlainElement equals = new PlainElement("equals", "=", builder);
        equals.setLeftPad(1);
        equals.setRightPad(1);
        parent.add(equals);

        value.model(builder, parent);
    }

}
