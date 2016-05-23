package org.obicere.bytecode.viewer.modeler.label;

import org.obicere.bytecode.core.objects.label.LazyLabel;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class LazyLabelModeler implements Modeler<LazyLabel> {
    @Override
    public void model(final LazyLabel element, final DocumentBuilder builder) {
        if(element.isInitialized()){
            builder.model(element.get());
        }   else {
            builder.add("?");
        }
    }
}
