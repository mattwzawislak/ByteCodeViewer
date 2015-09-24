package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;

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
}
