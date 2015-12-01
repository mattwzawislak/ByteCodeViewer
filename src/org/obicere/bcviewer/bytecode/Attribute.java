package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class Attribute extends ByteCodeElement {

    public String getIdentifier() {
        return "attribute" + getStart();
    }

}
