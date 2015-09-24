package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;

/**
 */
public class ExtendsBoundedWildcardIndicator extends WildcardIndicator {

    private final ReferenceTypeSignature referenceTypeSignature;

    public ExtendsBoundedWildcardIndicator(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static SuperBoundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        // optional +
        if (string.peek() == '+') {
            string.next();
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);
        if (referenceTypeSignature == null) {
            return null;
        }
        return new SuperBoundedWildcardIndicator(referenceTypeSignature);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            return;
        }
        final Path next = path.next();
        final int kind = next.getTypePathKind();
        if (kind == Path.KIND_WILDCARD) {
            add(annotation);
        } else if(kind == Path.KIND_TYPE_ARGUMENT){
            referenceTypeSignature.walk(annotation, path);
        }
    }

}
