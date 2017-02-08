package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.DefaultJCClass;
import org.obicere.bytecode.core.objects.signature.ClassSignature;
import org.obicere.bytecode.core.objects.signature.SuperclassSignature;
import org.obicere.bytecode.core.objects.signature.SuperinterfaceSignature;
import org.obicere.bytecode.core.objects.signature.TypeParameter;
import org.obicere.bytecode.core.objects.signature.TypeParameters;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ClassSignatureModeler implements Modeler<ClassSignature> {
    @Override
    public void model(final ClassSignature element, final DocumentBuilder builder) {
        // we are gonna have to assume that since this is a class signature
        // that we are modelling the actual class right now.
        // This means the class information is accessible through the
        // working class. However, if for any reason this is called and
        // this signature is NOT for the working class, then invalid
        // results may be produced, in correspondence to the
        // extends/implements and possible exclusion of the superclass
        // (if the working class is an interface)

        final DefaultJCClass workingClass = builder.getClassFile();
        final int flags = workingClass.getAccessFlags();

        final boolean isInterface = ByteCodeUtils.isInterface(flags);

        final TypeParameters typeParameters = element.getTypeParameters();
        final SuperclassSignature signature = element.getSuperclassSignature();
        final SuperinterfaceSignature[] signatures = element.getSuperinterfaceSignatures();

        modelTypeParameters(builder, typeParameters);

        if (!isInterface) {
            modelSuperclass(builder, signature);
        }
        modelSuperinterfaces(builder, signatures, isInterface);
    }

    private void modelTypeParameters(final DocumentBuilder builder, final TypeParameters typeParameters) {
        final TypeParameter[] types = typeParameters.getTypeParameters();
        if (types.length == 0) {
            return;
        }
        builder.add("<");
        boolean first = true;
        for (final TypeParameter type : types) {
            if (!first) {
                builder.comma();
            }
            builder.model(type);
            first = false;
        }
        builder.add(">");
    }

    private void modelSuperclass(final DocumentBuilder builder, final SuperclassSignature signature) {
        final boolean extendsObject = builder.getDomain().getSettingsController().getSettings().getBoolean("code.extendsObject", true);
        if (extendsObject || !signature.toString().equals("java.lang.Object")) {
            builder.addKeyword(" extends ");
            builder.model(signature);
        }
    }

    private void modelSuperinterfaces(final DocumentBuilder builder, final SuperinterfaceSignature[] signatures, final boolean isInterface) {
        boolean first = true;

        for (final SuperinterfaceSignature signature : signatures) {
            if (first) {
                if (isInterface) {
                    // interface extends other interfaces
                    builder.addKeyword(" extends ");
                } else {
                    builder.addKeyword(" implements ");
                }
            } else {
                builder.comma();
            }

            builder.model(signature);
            first = false;
        }
    }
}
