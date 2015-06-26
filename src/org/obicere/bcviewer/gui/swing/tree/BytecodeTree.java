package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.bytecode.ClassFile;

import javax.swing.JTree;

/**
 * @author Obicere
 */
public class BytecodeTree extends JTree {

    public BytecodeTree(final ClassFile file){
        setCellRenderer(new BytecodeTreeCellRenderer());
    }

}
