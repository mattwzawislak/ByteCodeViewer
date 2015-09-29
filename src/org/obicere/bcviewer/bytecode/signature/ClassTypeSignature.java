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
public class ClassTypeSignature extends ReferenceTypeSignature {

    private final PackageSpecifier           packageSpecifier;
    private final SimpleClassTypeSignature   simpleClassTypeSignature;
    private final ClassTypeSignatureSuffix[] classTypeSignatureSuffix;

    private ClassTypeSignature(final PackageSpecifier packageSpecifier, final SimpleClassTypeSignature simpleClassTypeSignature, final ClassTypeSignatureSuffix[] classTypeSignatureSuffix) {
        this.packageSpecifier = packageSpecifier;
        this.simpleClassTypeSignature = simpleClassTypeSignature;
        this.classTypeSignatureSuffix = classTypeSignatureSuffix;
    }

    public PackageSpecifier getPackageSpecifier() {
        return packageSpecifier;
    }

    public SimpleClassTypeSignature getSimpleClassTypeSignature() {
        return simpleClassTypeSignature;
    }

    public ClassTypeSignatureSuffix[] getClassTypeSignatureSuffix() {
        return classTypeSignatureSuffix;
    }

    public static ClassTypeSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != 'L') {
            return null;
        }
        final PackageSpecifier packageSpecifier = PackageSpecifier.parse(string);
        if (packageSpecifier == null) {
            return null;
        }
        final SimpleClassTypeSignature simpleClassTypeSignature = SimpleClassTypeSignature.parse(string);
        if (simpleClassTypeSignature == null) {
            return null;
        }
        if (!string.hasNext(';')) {
            return null;
        }
        final LinkedList<ClassTypeSignatureSuffix> suffixList = new LinkedList<>();
        char next = string.peek();
        while (string.hasNext() && next != ';') {
            final ClassTypeSignatureSuffix suffix = ClassTypeSignatureSuffix.parse(string);
            if (suffix == null) {
                return null;
            }
            suffixList.add(suffix);
            next = string.peek();
        }
        if (string.next() != ';') {
            return null;
        }
        return new ClassTypeSignature(packageSpecifier, simpleClassTypeSignature, suffixList.toArray(new ClassTypeSignatureSuffix[suffixList.size()]));
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        if (!path.hasNext()) {
            // we found an annotation
            add(annotation);
            return;
        }
        final Path next = path.next();
        final int kind = next.getTypePathKind();
        if (kind == Path.KIND_NESTED) {
            walkNested(annotation, path);
        } else if (kind == Path.KIND_TYPE_ARGUMENT) {
            final TypeArguments typeArguments = simpleClassTypeSignature.getTypeArguments();
            final TypeArgument[] types = typeArguments.getTypeArguments();
            final TypeArgument type = types[next.getTypeArgumentIndex()];
            final WildcardIndicator wildcardIndicator = type.getWildcardIndicator();
            wildcardIndicator.walk(annotation, path);
        }
    }

    private void walkNested(final TypeAnnotation annotation, final Iterator<Path> path) {

        int i = 0;
        ClassTypeSignatureSuffix suffix = classTypeSignatureSuffix[0];
        while (path.hasNext()) {
            final Path next = path.next();
            final int kind = next.getTypePathKind();
            if (kind == Path.KIND_NESTED) {
                suffix = classTypeSignatureSuffix[++i];
            } else if (kind == Path.KIND_TYPE_ARGUMENT) {
                final SimpleClassTypeSignature simple = suffix.getSimpleClassTypeSignature();
                final TypeArguments typeArguments = simple.getTypeArguments();
                final TypeArgument[] types = typeArguments.getTypeArguments();
                final TypeArgument type = types[next.getTypeArgumentIndex()];
                final WildcardIndicator wildcardIndicator = type.getWildcardIndicator();
                wildcardIndicator.walk(annotation, path);
                return;
            }
        }
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : getAnnotations()) {
            annotation.model(builder, parent);
        }
        modelPackage(builder, parent);
        modelSignature(builder, parent);
        modelSuffixes(builder, parent);
        builder.pad(parent, 1);
    }

    private void modelPackage(final BytecodeDocumentBuilder builder, final Element parent) {
        final String[] packageIdentifiers = packageSpecifier.getIdentifiers();
        for (final String identifier : packageIdentifiers) {
            builder.addPlain(parent, identifier + ".");
        }
    }

    private void modelSignature(final BytecodeDocumentBuilder builder, final Element parent) {

        builder.addPlain(parent, simpleClassTypeSignature.getIdentifier());

        final TypeArguments arguments = simpleClassTypeSignature.getTypeArguments();
        modelTypeArguments(builder, parent, arguments);
    }

    private void modelSuffixes(final BytecodeDocumentBuilder builder, final Element parent) {
        for (final ClassTypeSignatureSuffix suffix : classTypeSignatureSuffix) {
            final SimpleClassTypeSignature signature = suffix.getSimpleClassTypeSignature();

            builder.addPlain(parent, "." + signature.getIdentifier());

            final TypeArguments arguments = signature.getTypeArguments();
            modelTypeArguments(builder, parent, arguments);
        }
    }

    private void modelTypeArguments(final BytecodeDocumentBuilder builder, final Element parent, final TypeArguments typeArguments) {
        final TypeArgument[] types = typeArguments.getTypeArguments();
        if (types.length == 0) {
            return;
        }
        builder.addPlain(parent, "<");
        boolean first = true;
        for (final TypeArgument type : types) {
            if (!first) {
                builder.comma(parent);
            }
            type.getWildcardIndicator().model(builder, parent);
            first = false;
        }
        builder.addPlain(parent, ">");
    }
}