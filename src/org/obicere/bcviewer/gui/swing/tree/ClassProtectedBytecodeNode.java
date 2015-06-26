package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class ClassProtectedBytecodeNode extends BytecodeNode {

    public ClassProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_CLASS_PROTECTED), icons.getIcon(Icons.ICON_CLASS_DISABLED));
    }
}
