package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

/**
 */
public class ObjectVariableInfo extends VerificationTypeInfo {

    private final int index;

    public ObjectVariableInfo(final int tag, final int index) {
        super(tag);
        this.index = index;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {

        final String signature = builder.getConstantPool().getAsString(index);
        final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);
        if (fieldSignature != null) {
            fieldSignature.model(builder, parent);
        } else {
            builder.addPlain(BytecodeUtils.getQualifiedName(signature));
        }
    }
}
