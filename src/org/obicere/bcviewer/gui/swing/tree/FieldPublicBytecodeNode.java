package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

import javax.swing.Icon;

/**
 * @author Obicere
 */
public class FieldPublicBytecodeNode extends BytecodeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public FieldPublicBytecodeNode(final Icons icons){
        this.icon = icons.getIcon(Icons.ICON_FIELD_PUBLIC);
        this.disabledIcon = icons.getIcon(Icons.ICON_FIELD_DISABLED);
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
