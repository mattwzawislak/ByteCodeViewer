package org.obicere.bcviewer.dom.swing;

/**
 */
public class Caret {

    private int row;
    private int column;

    private JDocumentArea owner;

    public Caret(final JDocumentArea owner){
        this.owner = owner;
    }

    public JDocumentArea getOwner(){
        return owner;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }



}
