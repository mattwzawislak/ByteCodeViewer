package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignatureSuffix;
import org.obicere.bytecode.core.objects.signature.PackageSpecifier;
import org.obicere.bytecode.core.objects.signature.SimpleClassTypeSignature;
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

        if (!modelObject) {
            // we need to check to see if the signature is equal to
            // java.lang.Object, and if so we can skip it

            // TODO, not sure if a toString implementation would be able
            // to handle this by checking:
            // packageSpecifier.toString().equals("java.lang.");
        }

        final Set<Annotation> annotations = element.getAnnotations();
        final PackageSpecifier packageSpecifier = element.getPackageSpecifier();
        final SimpleClassTypeSignature simpleClassTypeSignature = element.getSimpleClassTypeSignature();
        final ClassTypeSignatureSuffix[] classTypeSignatureSuffix = element.getClassTypeSignatureSuffix();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }

        // otherwise we are importing the class and skip it
        builder.model(packageSpecifier);
        builder.model(simpleClassTypeSignature);
        for (final ClassTypeSignatureSuffix suffix : classTypeSignatureSuffix) {
            builder.model(suffix);
        }
    }
}
