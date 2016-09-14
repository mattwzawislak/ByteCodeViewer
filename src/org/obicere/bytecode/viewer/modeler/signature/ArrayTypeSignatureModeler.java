package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.signature.ArrayTypeSignature;
import org.obicere.bytecode.core.objects.signature.JavaTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class ArrayTypeSignatureModeler implements Modeler<ArrayTypeSignature> {
    @Override
    public void model(final ArrayTypeSignature element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();
        final JavaTypeSignature signature = element.getJavaTypeSignature();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }

        builder.model(signature);
        builder.add("[]");
        // realistically, this should do the same thing...
        // I think I fucked this up badly

        /**
        final LinkedList<ArrayTypeSignature> arrayList = new LinkedList<>();
        arrayList.add(this);
        JavaTypeSignature next = signature;
        // iterate all the arrays adding them to the array list, this
        // allows us to first model the component type and then the arrays
        while (next instanceof ArrayTypeSignature) {
            final ArrayTypeSignature array = (ArrayTypeSignature) next;
            arrayList.add(array);
            next = array.signature;
        }
        next.model(builder);
        for (final ArrayTypeSignature array : arrayList) {
            for (final Annotation annotation : array.getAnnotations()) {
                annotation.model(builder);
            }
            builder.add("[]");
        }
        */
    }
}
