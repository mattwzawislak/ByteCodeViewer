package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.JavaTypeSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        builder.addComment(parent, "Bootstrap Method:");
        modelDeclaration(builder, parent);

        builder.newLine(parent);
        builder.addPlain(parent, "Constant Arguments: {");
        builder.indent();
        for (final int argument : bootstrapArguments) {
            final Constant constant = constantPool.get(argument);
            builder.newLine(parent);
            constant.model(builder, parent);
        }
        builder.unindent();
        if(bootstrapArguments.length > 0){
            builder.newLine(parent);
        }
        builder.addPlain(parent, "}");
    }

    public void modelDeclaration(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String bootstrap = constantPool.getAsString(bootstrapMethodRef);
        final int signatureBegin = bootstrap.indexOf('(');
        final int pathBegin = bootstrap.indexOf('=');

        final String kind = bootstrap.substring(0, pathBegin);
        final String path = bootstrap.substring(pathBegin + 1, signatureBegin);
        final String signature = bootstrap.substring(signatureBegin);

        final String[] pathElements = path.split(";");
        builder.newLine(parent);

        builder.addKeyword(parent, kind + " ");

        boolean first = true;
        for (final String pathElement : pathElements) {
            if (!first) {
                builder.addPlain(parent, ".");
            }
            builder.addPlain(parent, BytecodeUtils.getQualifiedName(pathElement));
            first = false;
        }

        final MethodSignature methodSignature = SignatureAttribute.parseMethod(signature);
        builder.addPlain(parent, "(");
        builder.indent();

        final JavaTypeSignature[] types = methodSignature.getParameters();

        for (final JavaTypeSignature type : types) {
            builder.newLine(parent);
            type.model(builder, parent);
        }

        // close the parameters
        builder.unindent();

        // only break the () if there was a parameter
        if (types.length > 0) {
            builder.newLine(parent);
        }
        builder.addPlain(parent, ") ");
        methodSignature.modelReturnType(builder, parent);
    }

}
