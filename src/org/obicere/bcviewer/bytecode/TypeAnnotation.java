package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeAnnotation {

    private final int targetType;

    private final Target targetInfo;

    private final TypePath targetPath;

    private final int typeIndex;

    private final ElementValuePair[] elementValuePairs;

    public TypeAnnotation(final int targetType, final Target targetInfo, final TypePath targetPath, final int typeIndex, final ElementValuePair[] elementValuePairs) {
        this.targetType = targetType;
        this.targetInfo = targetInfo;
        this.targetPath = targetPath;
        this.typeIndex = typeIndex;
        this.elementValuePairs = elementValuePairs;
    }

    public int getTargetType(){
        return targetType;
    }

    public Target getTargetInfo(){
        return targetInfo;
    }

    public TypePath getTargetPath(){
        return targetPath;
    }

    public int getTypeIndex(){
        return typeIndex;
    }

    public ElementValuePair[] getElementValuePairs(){
        return elementValuePairs;
    }

}
