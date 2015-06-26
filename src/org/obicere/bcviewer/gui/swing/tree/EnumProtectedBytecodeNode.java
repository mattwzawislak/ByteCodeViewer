package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class EnumProtectedBytecodeNode extends BytecodeNode {

    public EnumProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ENUM_PROTECTED), icons.getIcon(Icons.ICON_ENUM_DISABLED));
    }
}