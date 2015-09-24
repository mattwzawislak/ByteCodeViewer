package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.CollapsibleElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;

/**
 * @author Obicere
 */
public class BootstrapMethodsAttribute extends Attribute {

    private final BootstrapMethod[] bootstrapMethods;

    public BootstrapMethodsAttribute(final BootstrapMethod[] bootstrapMethods) {

        if (bootstrapMethods == null) {
            throw new NullPointerException("bootstrap methods not defined.");
        }

        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {

        final BootstrapMethod[] methods = bootstrapMethods;
        if (methods.length == 0) {
            return;
        }

        final PlainElement header = new PlainElement("header", "Bootstrap Methods:", builder);
        final CollapsibleElement element = new CollapsibleElement("collapse", builder);

        for (final BootstrapMethod method : methods) {
            final BasicElement line = new BasicElement(method.getIdentifier());
            method.model(builder, line);
            element.add(line);

        }
        parent.add(header);
        parent.add(element);

    }
}
