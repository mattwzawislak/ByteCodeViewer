package org.obicere.bcviewer.dom.gui.swing;

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
        setRow(row);
        setColumn(column);
        this.isPlaced = true;
    }

    public void setRow(final int row) {

        final int max = owner.getLineCount();
        if (row < 0) {
            this.row = 0;
        } else if (row >= max) {
            this.row = max - 1;
        } else {
            this.row = row;
        }
    }

    public void setColumn(final int column) {

        final int max = owner.getMaxLineLength();
        if (column < 0) {
            this.column = 0;
        } else if (column >= max) {
            this.column = max - 1;
        } else {
            this.column = column;
        }
    }

    public void remove(){
        isPlaced = false;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void moveUp() {
        setRow(row - 1);
    }

    public void moveDown() {
        setRow(row + 1);
    }

    public void moveLeft(){
        setColumn(column - 1);
    }

    public void moveRight(){
        setColumn(column + 1);
    }

}
