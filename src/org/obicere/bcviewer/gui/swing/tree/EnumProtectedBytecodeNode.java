package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

import javax.swing.Icon;

/**
 * @author Obicere
 */
public class EnumProtectedBytecodeNode extends BytecodeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public EnumProtectedBytecodeNode(final Icons icons){
        this.icon = icons.getIcon(Icons.ICON_ENUM_PROTECTED);
        this.disabledIcon = icons.getIcon(Icons.ICON_ENUM_DISABLED);
    }

    @Override
    public Icon getIcon(){
        return icon;
    }

    @Override
    public Icon getDisabledIcon(){
        return disabledIcon;
    }
}