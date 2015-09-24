package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Annotation;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

import java.util.Iterator;

/**
 */
public class SuperBoundedWildcardIndicator extends WildcardIndicator {

    private final ReferenceTypeSignature referenceTypeSignature;

    private SuperBoundedWildcardIndicator(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static SuperBoundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext() && string.next() != '-') {
            return null;
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
    public void model(final DocumentBuilder builder, final Element parent) {
        for(final Annotation annotation : getAnnotations()){
            annotation.model(builder, parent);
        }
        parent.add(new PlainElement("wildcard", "?", builder));
        final KeywordElement superKeyword = new KeywordElement("super", "super", builder);
        superKeyword.setLeftPad(1);
        superKeyword.setRightPad(1);
        parent.add(superKeyword);
        referenceTypeSignature.model(builder, parent);
    }
}
