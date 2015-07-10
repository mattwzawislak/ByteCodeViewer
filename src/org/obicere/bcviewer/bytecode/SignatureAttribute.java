package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SignatureAttribute extends Attribute {

    private final int signatureIndex;

    public SignatureAttribute(final int attributeNameIndex, final int attributeLength, final int signatureIndex) {
        super(attributeNameIndex, attributeLength);

        this.signatureIndex = signatureIndex;
    }
}
