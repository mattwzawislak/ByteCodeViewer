package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class StackMapTableAttribute implements Attribute {

    private final StackMapFrame[] entries;

    public StackMapTableAttribute(final StackMapFrame[] entries) {
        this.entries = entries;
    }

    public StackMapFrame[] getEntries() {
        return entries;
    }
}
