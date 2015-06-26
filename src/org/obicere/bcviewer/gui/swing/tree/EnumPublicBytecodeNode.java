package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class EnumPublicBytecodeNode extends BytecodeNode {

    public EnumPublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ENUM_PUBLIC), icons.getIcon(Icons.ICON_ENUM_DISABLED));
    }
}