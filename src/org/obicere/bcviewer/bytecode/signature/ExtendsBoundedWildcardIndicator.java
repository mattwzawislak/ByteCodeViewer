package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;
import java.util.Iterator;

/**
 */
public class ExtendsBoundedWildcardIndicator extends WildcardIndicator {

    private final ReferenceTypeSignature referenceTypeSignature;

    private final boolean wildcardPresent;

    private ExtendsBoundedWildcardIndicator(final ReferenceTypeSignature referenceTypeSignature, final boolean wildcardPresent) {
        this.referenceTypeSignature = referenceTypeSignature;
        this.wildcardPresent = wildcardPresent;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public boolean isWildcardPresent() {
        return wildcardPresent;
    }

    public static ExtendsBoundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        boolean wildcardPresent = false;
        // optional +
        if (string.peek() == '+') {
            string.next();
            wildcardPresent = true;
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);
        if (referenceTypeSignature == null) {
            return null;
        }
        return new ExtendsBoundedWildcardIndicator(referenceTypeSignature, wildcardPresent);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            add(annotation);
            return;
        }
        final Path next = path.next();
        final int kind = next.getTypePathKind();
        if (kind == Path.KIND_WILDCARD) {
            add(annotation);
        } else if (kind == Path.KIND_TYPE_ARGUMENT) {
            referenceTypeSignature.walk(annotation, path);
        }
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        for(final Annotation annotation : getAnnotations()){
            annotation.model(builder, parent);
        }
        if (wildcardPresent) {
            builder.addPlain("?");
            builder.addKeyword(" extends ");
        }
        referenceTypeSignature.model(builder, parent);
    }
}
