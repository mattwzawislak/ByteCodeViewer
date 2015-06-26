package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class MethodDefaultBytecodeNode extends BytecodeNode {

    public MethodDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_METHOD_DEFAULT), icons.getIcon(Icons.ICON_METHOD_DISABLED));
    }
}
