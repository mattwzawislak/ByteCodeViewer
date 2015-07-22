package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class InnerClassesAttribute implements Attribute {

    private final InnerClass[] classes;

    public InnerClassesAttribute(final InnerClass[] classes){

        if(classes == null){
            throw new NullPointerException("inner classes not defined.");
        }

        this.classes = classes;
    }

    public InnerClass[] getInnerClasses(){
        return classes;
    }

}
