package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class MethodParametersAttribute extends Attribute {

    private final Parameter[] parameters;

    public MethodParametersAttribute(final int attributeNameIndex, final int attributeLength, final Parameter[] parameters) {
        super(attributeNameIndex, attributeLength);

        if (parameters == null) {
            throw new NullPointerException("parameters not defined.");
        }

        this.parameters = parameters;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

}
