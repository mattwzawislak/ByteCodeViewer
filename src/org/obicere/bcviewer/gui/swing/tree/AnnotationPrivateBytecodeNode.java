package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class AnnotationPrivateBytecodeNode extends BytecodeNode {

    public AnnotationPrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ANNOTATION_PRIVATE), icons.getIcon(Icons.ICON_ANNOTATION_DISABLED));
    }
}
