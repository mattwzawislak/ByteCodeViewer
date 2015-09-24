package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.AnnotationElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

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
    public void model(final DocumentBuilder builder, final Element parent) {
        final Element line = new BasicElement(getIdentifier(), Element.AXIS_LINE);
        final String identifier = BytecodeUtils.getQualifiedName(builder.getConstantPool().getAsString(typeIndex));

        // substring to remove the leading L and trailing ;
        final AnnotationElement name = new AnnotationElement("name", identifier.substring(1, identifier.length() - 1), builder);
        name.setRightPad(1);
        line.add(name);
        if (elementValuePairs.length > 0) {
            line.add(new PlainElement("open", "(", builder));

            boolean first = true;
            for (final ElementValuePair elementValuePair : elementValuePairs) {
                if (!first) {
                    final PlainElement element = new PlainElement("comma", ",", builder);
                    element.setRightPad(1);
                    line.add(element);
                }
                elementValuePair.model(builder, line);
                first = false;
            }

            final PlainElement close = new PlainElement("close", ")", builder);
            close.setRightPad(1);
            line.add(close);
        }
        parent.add(line);
    }
}
