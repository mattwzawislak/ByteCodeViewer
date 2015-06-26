package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class MethodPrivateBytecodeNode extends BytecodeNode {

    public MethodPrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_METHOD_PRIVATE), icons.getIcon(Icons.ICON_METHOD_DISABLED));
    }
}
