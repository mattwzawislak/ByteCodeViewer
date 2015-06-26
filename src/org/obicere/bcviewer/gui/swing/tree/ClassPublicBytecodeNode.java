package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

import javax.swing.Icon;

/**
 * @author Obicere
 */
public class ClassPublicBytecodeNode extends BytecodeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public ClassPublicBytecodeNode(final Icons icons){
        this.icon = icons.getIcon(Icons.ICON_CLASS_PUBLIC);
        this.disabledIcon = icons.getIcon(Icons.ICON_CLASS_DISABLED);
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public Icon getDisabledIcon() {
        return null;
    }
}
