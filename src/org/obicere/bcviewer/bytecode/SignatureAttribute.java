package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.ClassSignature;
import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.bytecode.signature.QueueString;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;

/**
 * @author Obicere
 */
public class SignatureAttribute extends Attribute {

    private final int signatureIndex;

    public SignatureAttribute(final int signatureIndex) {

        this.signatureIndex = signatureIndex;
    }

    public int getSignatureIndex() {
        return signatureIndex;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        throw new UnsupportedOperationException("signatures cannot be modeled properly. Instead call the parseXX methods.");
    }

    public FieldSignature parseField(final ConstantPool constantPool) {
        return FieldSignature.parse(new QueueString(constantPool.getAsString(signatureIndex)));
    }

    public MethodSignature parseMethod(final ConstantPool constantPool) {
        return MethodSignature.parse(new QueueString(constantPool.getAsString(signatureIndex)));
    }


    public ClassSignature parseClass(final ConstantPool constantPool) {
        return ClassSignature.parse(new QueueString(constantPool.getAsString(signatureIndex)));
    }


}
