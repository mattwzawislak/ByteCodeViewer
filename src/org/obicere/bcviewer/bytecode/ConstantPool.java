package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.CollapsibleElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;

/**
 * @author Obicere
 */
public class ConstantPool extends BytecodeElement {

    private final Constant[] constants;

    public ConstantPool(final Constant[] constants) {
        this.constants = constants;
    }

    public Constant get(final int index) {
        return constants[index];
    }

    public String getAsString(final int index) {
        final Constant constant = get(index);
        if (constant == null) {
            return "<null entry>";
        }
        return constant.toString(this);
    }

    public Constant[] getConstants() {
        return constants;
    }

    @Override
    public String getIdentifier() {
        return "constantPool";
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final TextElement element = new TextElement("constantPool", "Constant Pool:");
        element.setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_PLAIN));

        final CollapsibleElement collapse = new CollapsibleElement("collapse", builder);
        collapse.setAxis(Element.AXIS_PAGE);
        // start at i=1 to avoid the always-null and never used constant
        for (int i = 1; i < constants.length; i++) {
            final Element constantElement = new BasicElement("constant" + i);
            constantElement.setAxis(Element.AXIS_LINE);

            final Constant constant = constants[i];
            if (constant == null) {
                // maybe move this to a ConstantNull class with a modeler there?
                constantElement.add(new ParameterPlainElement("constant", "null constant", builder));
            } else {
                constant.model(builder, constantElement);
            }

            collapse.add(constantElement);
        }
        element.add(collapse);
        parent.add(element);
    }
}
