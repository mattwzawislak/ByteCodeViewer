package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

import javax.swing.Icon;

/**
 * @author Obicere
 */
public class EnumDefaultBytecodeNode extends BytecodeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public EnumDefaultBytecodeNode(final Icons icons) {
        this.icon = icons.getIcon(Icons.ICON_ENUM_DEFAULT);
        this.disabledIcon = icons.getIcon(Icons.ICON_ENUM_DISABLED);
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public Icon getDisabledIcon() {
        return disabledIcon;
    }

}
