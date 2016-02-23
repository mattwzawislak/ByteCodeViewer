package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignatureSuffix;
import org.obicere.bytecode.core.objects.signature.PackageSpecifier;
import org.obicere.bytecode.core.objects.signature.SimpleClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.TypeArgument;
import org.obicere.bytecode.core.objects.signature.TypeArguments;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;
import org.obicere.bytecode.viewer.settings.Settings;

import java.util.Set;

/**
 */
public class ClassTypeSignatureModeler implements Modeler<ClassTypeSignature> {
    @Override
    public void model(final ClassTypeSignature element, final DocumentBuilder builder) {
        final Settings settings = builder.getDomain().getSettingsController().getSettings();
        final boolean modelObject = settings.getBoolean("code.extendsObject", true);
        final boolean importMode = settings.getBoolean("code.importMode", true);

        if(!modelObject){
            // we need to check to see if the signature is equal to
            // java.lang.Object, and if so we can skip it
        }

        final Set<Annotation> annotations = element.getAnnotations();
        final PackageSpecifier packageSpecifier = element.getPackageSpecifier();
        final SimpleClassTypeSignature simpleClassTypeSignature = element.getSimpleClassTypeSignature();
        final ClassTypeSignatureSuffix[] classTypeSignatureSuffix = element.getClassTypeSignatureSuffix();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }

        // otherwise we are importing the class and skip it
        if (!importMode) {
            builder.model(packageSpecifier);
        }
        builder.model(simpleClassTypeSignature);
        for(final ClassTypeSignatureSuffix suffix : classTypeSignatureSuffix){
            builder.model(suffix);
        }
    }

    private void modelPackage(final DocumentBuilder builder, final PackageSpecifier packageSpecifier) {
        final String[] packageIdentifiers = packageSpecifier.getIdentifiers();
        for (final String identifier : packageIdentifiers) {
            builder.add(identifier);
            builder.add(".");
        }
    }

    private void modelSignature(final DocumentBuilder builder, final SimpleClassTypeSignature simpleClassTypeSignature) {

        builder.add(simpleClassTypeSignature.getIdentifier());

        final TypeArguments arguments = simpleClassTypeSignature.getTypeArguments();
        modelTypeArguments(builder, arguments);
    }

    private void modelSuffixes(final DocumentBuilder builder, final ClassTypeSignatureSuffix[] classTypeSignatureSuffix) {
        for (final ClassTypeSignatureSuffix suffix : classTypeSignatureSuffix) {
            final SimpleClassTypeSignature signature = suffix.getSimpleClassTypeSignature();

            builder.add(".");
            builder.add(signature.getIdentifier());

            final TypeArguments arguments = signature.getTypeArguments();
            modelTypeArguments(builder, arguments);
        }
    }

    private void modelTypeArguments(final DocumentBuilder builder, final TypeArguments typeArguments) {
        final TypeArgument[] types = typeArguments.getTypeArguments();
        if (types.length == 0) {
            return;
        }
        builder.add("<");
        boolean first = true;
        for (final TypeArgument type : types) {
            if (!first) {
                builder.comma();
            }
            builder.model(type);
            first = false;
        }
        builder.add(">");
    }
}
