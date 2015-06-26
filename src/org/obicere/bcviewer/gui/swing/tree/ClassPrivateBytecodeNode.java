package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

import javax.swing.Icon;

/**
 * @author Obicere
 */
public class ClassPrivateBytecodeNode extends BytecodeNode {

    private final Icon icon;
    private final Icon disabledIcon;

    public ClassPrivateBytecodeNode(final Icons icons) {
        this.icon = icons.getIcon(Icons.ICON_ANNOTATION_PRIVATE);
        this.disabledIcon = icons.getIcon(Icons.ICON_CLASS_DISABLED);
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
