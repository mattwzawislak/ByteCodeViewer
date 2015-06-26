package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class AnnotationPublicBytecodeNode extends BytecodeNode {

    public AnnotationPublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ANNOTATION_PUBLIC), icons.getIcon(Icons.ICON_ANNOTATION_DISABLED));
    }

}
