package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.TypeParameterBoundTarget;

import java.util.LinkedList;

/**
 */
public class MethodSignature {

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
}
