package org.obicere.bcviewer.dom.swing;

/**
 */
public class Caret {

    private int row;
    private int column;

    private boolean isPlaced = false;

    private JDocumentArea owner;

    public Caret(final JDocumentArea owner) {
        this.owner = owner;
    }

    public JDocumentArea getOwner() {
        return owner;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setLocation(final int row, final int column) {
        this.row = row;
        this.column = column;
        this.isPlaced = true;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

}
