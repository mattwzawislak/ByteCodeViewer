package org.obicere.bcviewer.util;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public interface Modeler<T> {

    public boolean model(final T value, final Element element);

}
