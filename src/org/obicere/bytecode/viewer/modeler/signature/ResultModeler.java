package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.JavaTypeSignature;
import org.obicere.bytecode.core.objects.signature.Result;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class ResultModeler implements Modeler<Result> {
    @Override
    public void model(final Result element, final DocumentBuilder builder) {
        final JavaTypeSignature signature = element.getJavaTypeSignature();

        builder.model(signature);
    }
}
