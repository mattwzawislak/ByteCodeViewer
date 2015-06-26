package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class MethodPublicBytecodeNode extends BytecodeNode {

    public MethodPublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_METHOD_PUBLIC), icons.getIcon(Icons.ICON_METHOD_DISABLED));
    }
}
