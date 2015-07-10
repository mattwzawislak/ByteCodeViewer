package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class Path {

    private final int typePathKind;

    private final int typeArgumentIndex;

    public Path(final int typePathKind, final int typeArgumentIndex){
        this.typePathKind = typePathKind;
        this.typeArgumentIndex = typeArgumentIndex;
    }

    public int getTypePathKind(){
        return typePathKind;
    }

    public int getTypeArgumentIndex(){
        return typeArgumentIndex;
    }

}
