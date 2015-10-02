package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 */
public class ObjectVariableInfo extends VerificationTypeInfo {

    private final int index;

    public ObjectVariableInfo(final int tag, final int index) {
        super(tag);
        this.index = index;
    }

    @Override
    public void model(final DocumentBuilder builder) {

        final String signature = builder.getConstantPool().getAsString(index);
        final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);
        if (fieldSignature != null) {
            fieldSignature.model(builder);
        } else {
            builder.add(BytecodeUtils.getQualifiedName(signature));
        }
    }
}
