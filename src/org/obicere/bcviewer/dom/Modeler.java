package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public interface Modeler<T> {

    public Element model(final DocumentBuilder builder, final Element parent);

}
