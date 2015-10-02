package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.JavaTypeSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 * @author Obicere
 */
public class BootstrapMethod extends BytecodeElement {

    private final int   bootstrapMethodRef;
    private final int[] bootstrapArguments;

    public BootstrapMethod(final int bootstrapMethodRef, final int[] bootstrapArguments) {

        if (bootstrapArguments == null) {
            throw new NullPointerException("bootstrap arguments not defined.");
        }

        this.bootstrapMethodRef = bootstrapMethodRef;
        this.bootstrapArguments = bootstrapArguments;
    }

    public int getBootstrapMethodRef() {
        return bootstrapMethodRef;
    }

    public int[] getBootstrapArguments() {
        return bootstrapArguments;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        builder.addComment("Bootstrap Method:");
        modelDeclaration(builder);

        builder.newLine();
        builder.add("Constant Arguments: {");
        builder.indent();
        for (final int argument : bootstrapArguments) {
            final Constant constant = constantPool.get(argument);
            builder.newLine();
            constant.model(builder);
        }
        builder.unindent();
        if(bootstrapArguments.length > 0){
            builder.newLine();
        }
        builder.add("}");
    }

    public void modelDeclaration(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String bootstrap = constantPool.getAsString(bootstrapMethodRef);
        final int signatureBegin = bootstrap.indexOf('(');
        final int pathBegin = bootstrap.indexOf('=');

        final String kind = bootstrap.substring(0, pathBegin);
        final String path = bootstrap.substring(pathBegin + 1, signatureBegin);
        final String signature = bootstrap.substring(signatureBegin);

        final String[] pathElements = path.split(";");
        builder.newLine();

        builder.addKeyword(kind + " ");

        boolean first = true;
        for (final String pathElement : pathElements) {
            if (!first) {
                builder.add(".");
            }
            builder.add(BytecodeUtils.getQualifiedName(pathElement));
            first = false;
        }

        final MethodSignature methodSignature = SignatureAttribute.parseMethod(signature);
        builder.add("(");
        builder.indent();

        final JavaTypeSignature[] types = methodSignature.getParameters();

        for (final JavaTypeSignature type : types) {
            builder.newLine();
            type.model(builder);
        }

        // close the parameters
        builder.unindent();

        // only break the () if there was a parameter
        if (types.length > 0) {
            builder.newLine();
        }
        builder.add(") ");
        methodSignature.modelReturnType(builder);
    }

}
