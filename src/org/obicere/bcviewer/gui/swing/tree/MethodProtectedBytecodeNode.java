package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class MethodProtectedBytecodeNode extends BytecodeNode {

    public MethodProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_METHOD_PROTECTED), icons.getIcon(Icons.ICON_METHOD_DISABLED));
    }
}
