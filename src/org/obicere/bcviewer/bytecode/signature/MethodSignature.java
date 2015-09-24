package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.MethodFormalParameterTarget;
import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.ThrowsTarget;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.bytecode.TypeParameterBoundTarget;
import org.obicere.bcviewer.bytecode.TypeParameterTarget;

import java.util.Iterator;
import java.util.LinkedList;

/**
 */
public class MethodSignature extends AnnotationTarget {

    private final TypeParameters typeParameters;

    private final JavaTypeSignature[] parameters;

    private final Result result;

    private final ThrowsSignature[] throwsSignatures;

    private MethodSignature(final TypeParameters typeParameters, final JavaTypeSignature[] parameters, final Result result, final ThrowsSignature[] throwsSignatures) {
        this.typeParameters = typeParameters;
        this.parameters = parameters;
        this.result = result;
        this.throwsSignatures = throwsSignatures;
    }

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public JavaTypeSignature[] getParameters() {
        return parameters;
    }

    public Result getResult() {
        return result;
    }

    public ThrowsSignature[] getThrowsSignatures() {
        return throwsSignatures;
    }

    public static MethodSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final TypeParameters typeParameters = TypeParameters.parse(string);
        if (typeParameters == null) {
            return null;
        }
        if (string.next() != '(') {
            return null;
        }
        if (!string.hasNext(')')) {
            return null;
        }
        final LinkedList<JavaTypeSignature> javaTypeSignatureList = new LinkedList<>();
        while (string.peek() != ')') {
            final JavaTypeSignature javaTypeSignature = JavaTypeSignature.parse(string);
            if (javaTypeSignature == null) {
                return null;
            }
            javaTypeSignatureList.add(javaTypeSignature);
        }
        final JavaTypeSignature[] javaTypeSignatures = javaTypeSignatureList.toArray(new JavaTypeSignature[javaTypeSignatureList.size()]);
        string.next();
        final Result result = Result.parse(string);
        if (result == null) {
            return null;
        }
        final LinkedList<ThrowsSignature> throwsList = new LinkedList<>();
        while (string.hasNext()) {
            final ThrowsSignature throwsSignature = ThrowsSignature.parse(string);
            if (throwsSignature == null) {
                return null;
            }
            throwsList.add(throwsSignature);
        }
        final ThrowsSignature[] throwsSignatures = throwsList.toArray(new ThrowsSignature[throwsList.size()]);
        return new MethodSignature(typeParameters, javaTypeSignatures, result, throwsSignatures);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        final int targetType = annotation.getTargetType();
        switch (targetType) {
            case 0x01: // type_parameter_target
                walkTypeParameterTarget(annotation, path);
                return;
            case 0x12: // type_parameter_bound_target
                walkTypeParameterBoundTarget(annotation, path);
                return;
            case 0x14: // empty_target (return type)
                walkEmptyTargetReturn(annotation, path);
                return;
            case 0x15: // empty_target (receiver type)
                walkEmptyTargetReceiver(annotation, path);
                return;
            case 0x16: // formal_parameter_target
                walkFormalParameterTarget(annotation, path);
                return;
            case 0x17: // throws_target
                walkThrowsTarget(annotation, path);
                return;
            default:

        }
    }

    private void walkTypeParameterTarget(final TypeAnnotation annotation, final Iterator<Path> path) {
        final TypeParameterTarget target = (TypeParameterTarget) annotation.getTargetInfo();
        final TypeParameter[] types = typeParameters.getTypeParameters();
        types[target.getTypeParameterIndex()].walk(annotation, path);
    }

    private void walkTypeParameterBoundTarget(final TypeAnnotation annotation, final Iterator<Path> path) {
        final TypeParameterBoundTarget target = (TypeParameterBoundTarget) annotation.getTargetInfo();
        final int typeParameterIndex = target.getTypeParameterIndex();
        final int boundIndex = target.getBoundIndex();
        final TypeParameter[] types = typeParameters.getTypeParameters();
        final TypeParameter type = types[typeParameterIndex];
        final ClassBound classBound = type.getClassBound();
        final InterfaceBound[] interfaceBounds = type.getInterfaceBounds();
        final boolean hasClassBound = classBound.getReferenceTypeSignature() != null;
        if (boundIndex == 0) {
            if (hasClassBound) {
                classBound.walk(annotation, path);
            } else {
                interfaceBounds[0].walk(annotation, path);
            }
        } else {
            final int fixedIndex;
            if (hasClassBound) {
                fixedIndex = boundIndex - 1;
            } else {
                fixedIndex = boundIndex;
            }
            interfaceBounds[fixedIndex].walk(annotation, path);
        }
    }

    private void walkEmptyTargetReturn(final TypeAnnotation annotation, final Iterator<Path> path) {
        result.walk(annotation, path);
    }

    private void walkEmptyTargetReceiver(final TypeAnnotation annotation, final Iterator<Path> path) {
        // first parameter is the receiver always - thus empty target
        parameters[0].walk(annotation, path);
    }

    private void walkFormalParameterTarget(final TypeAnnotation annotation, final Iterator<Path> path) {
        final MethodFormalParameterTarget formalParameter = (MethodFormalParameterTarget) annotation.getTargetInfo();
        parameters[formalParameter.getFormalParameterIndex()].walk(annotation, path);
    }

    private void walkThrowsTarget(final TypeAnnotation annotation, final Iterator<Path> path) {
        final ThrowsTarget throwsTarget = (ThrowsTarget) annotation.getTargetInfo();
        throwsSignatures[throwsTarget.getThrowsTypeIndex()].walk(annotation, path);
    }
}
