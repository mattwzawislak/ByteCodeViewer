package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;
import java.util.Iterator;
import java.util.LinkedList;

/**
 */
public class ArrayTypeSignature extends ReferenceTypeSignature {

    private final JavaTypeSignature signature;

    private ArrayTypeSignature(final JavaTypeSignature signature) {
        this.signature = signature;
    }

    public JavaTypeSignature getJavaTypeSignature() {
        return signature;
    }

    public static ArrayTypeSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != '[') {
            return null;
        }
        final JavaTypeSignature signature = JavaTypeSignature.parse(string);
        if (signature == null) {
            return null;
        }
        return new ArrayTypeSignature(signature);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            add(annotation);
        } else {
            final Path next = path.next();
            if (next.getTypePathKind() == Path.KIND_ARRAY) {
                signature.walk(annotation, path);
            }
        }
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : getAnnotations()) {
            annotation.model(builder, parent);
        }
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
        next.model(builder, parent);
        for (final ArrayTypeSignature array : arrayList) {
            for (final Annotation annotation : array.getAnnotations()) {
                annotation.model(builder, parent);
            }
            builder.addPlain(parent, "[]");
        }
    }
}
