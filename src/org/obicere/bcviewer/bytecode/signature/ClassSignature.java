package org.obicere.bcviewer.bytecode.signature;

import java.util.LinkedList;

/**
 */
public class ClassSignature {

    private final TypeParameters typeParameters;

    private final SuperclassSignature superclassSignature;

    private final SuperinterfaceSignature[] superinterfaceSignatures;

    private ClassSignature(final TypeParameters typeParameters, final SuperclassSignature superclassSignature, final SuperinterfaceSignature[] superinterfaceSignatures) {
        this.typeParameters = typeParameters;
        this.superclassSignature = superclassSignature;
        this.superinterfaceSignatures = superinterfaceSignatures;
    }

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public SuperclassSignature getSuperclassSignature() {
        return superclassSignature;
    }

    public SuperinterfaceSignature[] getSuperinterfaceSignatures() {
        return superinterfaceSignatures;
    }

    public static ClassSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final TypeParameters typeParameters = TypeParameters.parse(string);
        if (typeParameters == null) {
            return null;
        }
        final SuperclassSignature superclassSignature = SuperclassSignature.parse(string);
        if (superclassSignature == null) {
            return null;
        }
        final LinkedList<SuperinterfaceSignature> interfaceList = new LinkedList<>();
        while (string.hasNext()) {
            final SuperinterfaceSignature superinterfaceSignature = SuperinterfaceSignature.parse(string);
            if (superinterfaceSignature == null) {
                return null;
            }
            interfaceList.add(superinterfaceSignature);
        }
        final SuperinterfaceSignature[] superinterfaceSignatures = interfaceList.toArray(new SuperinterfaceSignature[interfaceList.size()]);
        return new ClassSignature(typeParameters, superclassSignature, superinterfaceSignatures);
    }

}
