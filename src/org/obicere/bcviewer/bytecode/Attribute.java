package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class Attribute extends BytecodeElement {

    public String getIdentifier() {
        return "attribute" + getStart();
    }

}
