package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.ParameterKeywordElement;

/**
 */
public class IntegerVariableInfo extends VerificationTypeInfo {

    public IntegerVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new ParameterKeywordElement("int", "int", builder));
    }

}
