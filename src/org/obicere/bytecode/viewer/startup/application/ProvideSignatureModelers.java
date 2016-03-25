package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.signature.ArrayTypeSignature;
import org.obicere.bytecode.core.objects.signature.BaseType;
import org.obicere.bytecode.core.objects.signature.ClassBound;
import org.obicere.bytecode.core.objects.signature.ClassSignature;
import org.obicere.bytecode.core.objects.signature.ClassThrowsSignature;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignatureSuffix;
import org.obicere.bytecode.core.objects.signature.ExtendsBoundedWildcardIndicator;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.InterfaceBound;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.PackageSpecifier;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.core.objects.signature.Result;
import org.obicere.bytecode.core.objects.signature.SimpleClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.SuperBoundedWildcardIndicator;
import org.obicere.bytecode.core.objects.signature.SuperclassSignature;
import org.obicere.bytecode.core.objects.signature.SuperinterfaceSignature;
import org.obicere.bytecode.core.objects.signature.ThrowsSignatures;
import org.obicere.bytecode.core.objects.signature.TypeArgument;
import org.obicere.bytecode.core.objects.signature.TypeArguments;
import org.obicere.bytecode.core.objects.signature.TypeParameter;
import org.obicere.bytecode.core.objects.signature.TypeParameters;
import org.obicere.bytecode.core.objects.signature.TypeThrowsSignature;
import org.obicere.bytecode.core.objects.signature.TypeVariableSignature;
import org.obicere.bytecode.core.objects.signature.UnboundedWildcardIndicator;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.signature.ArrayTypeSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.BaseTypeModeler;
import org.obicere.bytecode.viewer.modeler.signature.ClassBoundModeler;
import org.obicere.bytecode.viewer.modeler.signature.ClassSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.ClassThrowsSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.ClassTypeSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.ClassTypeSignatureSuffixModeler;
import org.obicere.bytecode.viewer.modeler.signature.ExtendsBoundedWildcardIndicatorModeler;
import org.obicere.bytecode.viewer.modeler.signature.FieldSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.InterfaceBoundModeler;
import org.obicere.bytecode.viewer.modeler.signature.MethodSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.PackageSpecifierModeler;
import org.obicere.bytecode.viewer.modeler.signature.ParametersModeler;
import org.obicere.bytecode.viewer.modeler.signature.ResultModeler;
import org.obicere.bytecode.viewer.modeler.signature.SimpleClassTypeSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.SuperBoundedWildcardIndicatorModeler;
import org.obicere.bytecode.viewer.modeler.signature.SuperclassSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.SuperinterfaceSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.ThrowsSignaturesModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeArgumentModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeArgumentsModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeParameterModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeParametersModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeThrowsSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.TypeVariableSignatureModeler;
import org.obicere.bytecode.viewer.modeler.signature.UnboundedWildcardIndicatorModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideSignatureModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(ArrayTypeSignature.IDENTIFIER, new ArrayTypeSignatureModeler());
        modelerSet.add(BaseType.IDENTIFIER, new BaseTypeModeler());
        modelerSet.add(ClassBound.IDENTIFIER, new ClassBoundModeler());
        modelerSet.add(ClassSignature.IDENTIFIER, new ClassSignatureModeler());
        modelerSet.add(ClassThrowsSignature.IDENTIFIER, new ClassThrowsSignatureModeler());
        modelerSet.add(ClassTypeSignature.IDENTIFIER, new ClassTypeSignatureModeler());
        modelerSet.add(ClassTypeSignatureSuffix.IDENTIFIER, new ClassTypeSignatureSuffixModeler());
        modelerSet.add(ExtendsBoundedWildcardIndicator.IDENTIFIER, new ExtendsBoundedWildcardIndicatorModeler());
        modelerSet.add(FieldSignature.IDENTIFIER, new FieldSignatureModeler());
        modelerSet.add(InterfaceBound.IDENTIFIER, new InterfaceBoundModeler());
        modelerSet.add(MethodSignature.IDENTIFIER, new MethodSignatureModeler());
        modelerSet.add(PackageSpecifier.IDENTIFIER, new PackageSpecifierModeler());
        modelerSet.add(Parameters.IDENTIFIER, new ParametersModeler());
        modelerSet.add(Result.IDENTIFIER, new ResultModeler());
        modelerSet.add(SimpleClassTypeSignature.IDENTIFIER, new SimpleClassTypeSignatureModeler());
        modelerSet.add(SuperBoundedWildcardIndicator.IDENTIFIER, new SuperBoundedWildcardIndicatorModeler());
        modelerSet.add(SuperclassSignature.IDENTIFIER, new SuperclassSignatureModeler());
        modelerSet.add(SuperinterfaceSignature.IDENTIFIER, new SuperinterfaceSignatureModeler());
        modelerSet.add(ThrowsSignatures.IDENTIFIER, new ThrowsSignaturesModeler());
        modelerSet.add(TypeArgument.IDENTIFIER, new TypeArgumentModeler());
        modelerSet.add(TypeArguments.IDENTIFIER, new TypeArgumentsModeler());
        modelerSet.add(TypeParameter.IDENTIFIER, new TypeParameterModeler());
        modelerSet.add(TypeParameters.IDENTIFIER, new TypeParametersModeler());
        modelerSet.add(TypeThrowsSignature.IDENTIFIER, new TypeThrowsSignatureModeler());
        modelerSet.add(TypeVariableSignature.IDENTIFIER, new TypeVariableSignatureModeler());
        modelerSet.add(UnboundedWildcardIndicator.IDENTIFIER, new UnboundedWildcardIndicatorModeler());
    }
}
