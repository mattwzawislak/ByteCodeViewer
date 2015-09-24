package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeAnnotation extends Annotation {

    private final int targetType;

    private final Target targetInfo;

    private final TypePath targetPath;

    public TypeAnnotation(final int targetType, final Target targetInfo, final TypePath targetPath, final int typeIndex, final ElementValuePair[] elementValuePairs) {
        super(typeIndex, elementValuePairs);
        this.targetType = targetType;
        this.targetInfo = targetInfo;
        this.targetPath = targetPath;
    }

    public int getTargetType() {
        return targetType;
    }

    public Target getTargetInfo() {
        return targetInfo;
    }

    public TypePath getTargetPath() {
        return targetPath;
    }
}
