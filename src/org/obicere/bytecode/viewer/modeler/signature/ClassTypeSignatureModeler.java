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
        final boolean importMode = settings.getBoolean("code.importMode", false);

        final Set<Annotation> annotations = element.getAnnotations();
        final PackageSpecifier packageSpecifier = element.getPackageSpecifier();
        final SimpleClassTypeSignature simpleClassTypeSignature = element.getSimpleClassTypeSignature();
        final ClassTypeSignatureSuffix[] classTypeSignatureSuffix = element.getClassTypeSignatureSuffix();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }

        if (!importMode) {
            builder.model(packageSpecifier);
        }
        builder.model(simpleClassTypeSignature);

        for (final ClassTypeSignatureSuffix suffix : classTypeSignatureSuffix) {
            builder.model(suffix);
        }
    }
}
