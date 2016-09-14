package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ClassTypeSignatureSuffix;
import org.obicere.bytecode.core.objects.signature.SimpleClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.TypeArguments;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class ClassTypeSignatureSuffixModeler implements Modeler<ClassTypeSignatureSuffix> {
    @Override
    public void model(final ClassTypeSignatureSuffix element, final DocumentBuilder builder) {
        final SimpleClassTypeSignature signature = element.getSimpleClassTypeSignature();

        // TODO add to simple class type signature
        final TypeArguments arguments = signature.getTypeArguments();

        builder.add(".");
        builder.model(signature);
        builder.model(arguments);
    }
}
