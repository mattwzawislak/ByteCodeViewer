package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.ParameterKeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

/**
 */
public class UninitializedThisVariableInfo extends VerificationTypeInfo {

    public UninitializedThisVariableInfo(final int tag) {
        super(tag);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new ParameterKeywordElement("this", "this", builder));
        parent.add(new PlainElement("arrow", "->", builder));
        parent.add(new KeywordElement("nullptr", "nullptr", builder));
    }

}
