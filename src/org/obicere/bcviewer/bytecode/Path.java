package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class Path {

    public static final int KIND_ARRAY         = 0;
    public static final int KIND_NESTED        = 1;
    public static final int KIND_WILDCARD      = 2;
    public static final int KIND_TYPE_ARGUMENT = 3;

    private final int typePathKind;

    private final int typeArgumentIndex;

    public Path(final int typePathKind, final int typeArgumentIndex) {
        this.typePathKind = typePathKind;
        this.typeArgumentIndex = typeArgumentIndex;
    }

    public int getTypePathKind() {
        return typePathKind;
    }

    public int getTypeArgumentIndex() {
        return typeArgumentIndex;
    }

}
