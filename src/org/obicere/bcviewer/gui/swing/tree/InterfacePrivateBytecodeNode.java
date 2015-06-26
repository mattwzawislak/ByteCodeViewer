package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class InterfacePrivateBytecodeNode extends BytecodeNode {

    public InterfacePrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_INTERFACE_PRIVATE), icons.getIcon(Icons.ICON_INTERFACE_DISABLED));
    }

}
