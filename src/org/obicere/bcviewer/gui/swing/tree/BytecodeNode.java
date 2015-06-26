package org.obicere.bcviewer.gui.swing.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Obicere
 */
public class BytecodeNode extends DefaultMutableTreeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public BytecodeNode(final Icon icon, final Icon disabledIcon) {
        super();
        this.icon = icon;
        this.disabledIcon = disabledIcon;
    }

    public BytecodeNode(final Icon icon, final Icon disabledIcon, final Object object) {
        super(object);
        this.icon = icon;
        this.disabledIcon = disabledIcon;
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getDisabledIcon() {
        return disabledIcon;
    }

}
