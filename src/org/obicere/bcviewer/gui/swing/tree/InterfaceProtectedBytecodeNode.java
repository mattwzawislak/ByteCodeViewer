package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class InterfaceProtectedBytecodeNode extends BytecodeNode {

    public InterfaceProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_INTERFACE_PROTECTED), icons.getIcon(Icons.ICON_INTERFACE_DISABLED));
    }

}
