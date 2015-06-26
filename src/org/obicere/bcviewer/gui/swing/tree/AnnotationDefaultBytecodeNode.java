package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class AnnotationDefaultBytecodeNode extends BytecodeNode {

    public AnnotationDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ANNOTATION_DEFAULT), icons.getIcon(Icons.ICON_ANNOTATION_DISABLED));
    }
}
