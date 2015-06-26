package org.obicere.bcviewer.gui.swing.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Obicere
 */
public abstract class BytecodeNode extends DefaultMutableTreeNode {

    public abstract Icon getIcon();

    public abstract Icon getDisabledIcon();

}
