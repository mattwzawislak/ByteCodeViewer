package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public interface Modeler<T> {

    public void model(final T element, final DocumentBuilder builder);

}
