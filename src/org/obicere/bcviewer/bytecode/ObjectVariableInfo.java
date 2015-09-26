package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.EmptyTextElement;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ObjectVariableInfo extends VerificationTypeInfo {

    private final int index;

    public ObjectVariableInfo(final int tag, final int index) {
        super(tag);
        this.index = index;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final TextElement element = new EmptyTextElement(builder);
        element.setLeftPad(builder.getTabSize());

        final String signature = builder.getConstantPool().getAsString(index);
        final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);
        fieldSignature.model(builder, parent);
    }

}
