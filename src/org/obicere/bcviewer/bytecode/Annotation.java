package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class Annotation extends BytecodeElement {

    private final int typeIndex;

    private final ElementValuePair[] elementValuePairs;

    public Annotation(final int typeIndex, final ElementValuePair[] elementValuePairs) {

        if (elementValuePairs == null) {
            throw new NullPointerException("element value pairs not defined.");
        }
        this.typeIndex = typeIndex;
        this.elementValuePairs = elementValuePairs;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public ElementValuePair[] getElementValuePairs() {
        return elementValuePairs;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final String identifier = BytecodeUtils.getQualifiedName(builder.getConstantPool().getAsString(typeIndex));
        builder.addAnnotation(identifier.substring(1, identifier.length() - 1));
        if (elementValuePairs.length > 0) {
            builder.addPlain("(");
            boolean first = true;
            for (final ElementValuePair elementValuePair : elementValuePairs) {
                if (!first) {
                    builder.comma();
                }
                elementValuePair.model(builder, parent);
                first = false;
            }
            builder.addPlain(")");
        }
    }
}
