package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;

/**
 */
public class UnboundedWildcardIndicator extends WildcardIndicator {

    public static UnboundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext() && string.next() != '*') {
            return null;
        }
        return new UnboundedWildcardIndicator();
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            return;
        }
        final Path next = path.next();
        if (next.getTypePathKind() == Path.KIND_WILDCARD) {
            add(annotation);
        }
    }
}
