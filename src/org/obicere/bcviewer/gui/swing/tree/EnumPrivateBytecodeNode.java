package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class EnumPrivateBytecodeNode extends BytecodeNode {

    public EnumPrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ENUM_PRIVATE), icons.getIcon(Icons.ICON_ENUM_DISABLED));
    }
}
