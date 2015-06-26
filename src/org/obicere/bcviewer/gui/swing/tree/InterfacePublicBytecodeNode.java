package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class InterfacePublicBytecodeNode extends BytecodeNode {

    public InterfacePublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_INTERFACE_PUBLIC), icons.getIcon(Icons.ICON_INTERFACE_DISABLED));
    }

}
