package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class EnumDefaultBytecodeNode extends BytecodeNode {

    public EnumDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ENUM_DEFAULT), icons.getIcon(Icons.ICON_ENUM_DISABLED));
    }

}
