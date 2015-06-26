package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class InterfaceDefaultBytecodeNode extends BytecodeNode {

    public InterfaceDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_INTERFACE_DEFAULT), icons.getIcon(Icons.ICON_INTERFACE_DISABLED));
    }
}
