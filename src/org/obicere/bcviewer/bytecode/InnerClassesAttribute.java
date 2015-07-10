package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class InnerClassesAttribute extends Attribute {

    private final InnerClass[] classes;

    public InnerClassesAttribute(final int attributeNameIndex, final int attributeLength, final InnerClass[] classes){
        super(attributeNameIndex, attributeLength);

        if(classes == null){
            throw new NullPointerException("inner classes not defined.");
        }

        this.classes = classes;
    }

}
