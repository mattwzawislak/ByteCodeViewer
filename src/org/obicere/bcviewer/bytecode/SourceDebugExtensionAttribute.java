package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SourceDebugExtensionAttribute extends Attribute {

    // Should be a byte[], but represents a string with no null-terminator.
    // GET TO THE CHO- Exception in thread "main" java.lang.NullPointerException:
    private final String debugExtension;

    public SourceDebugExtensionAttribute(final int attributeNameIndex, final int attributeLength, final String debugExtension) {
        super(attributeNameIndex, attributeLength);

        if (debugExtension.length() != attributeLength) {
            throw new IllegalArgumentException("invalid length for debug extension. Expected: " + attributeLength + ". Got: " + debugExtension.length());
        }

        this.debugExtension = debugExtension;
    }

    public String getDebugExtension() {
        return debugExtension;
    }

}
