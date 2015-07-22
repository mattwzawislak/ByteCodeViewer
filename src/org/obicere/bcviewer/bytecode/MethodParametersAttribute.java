package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class MethodParametersAttribute implements Attribute {

    private final Parameter[] parameters;

    public MethodParametersAttribute(final Parameter[] parameters) {

        if (parameters == null) {
            throw new NullPointerException("parameters not defined.");
        }

        this.parameters = parameters;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

}
