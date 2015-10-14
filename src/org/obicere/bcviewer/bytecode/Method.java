package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.Set;

/**
 * @author Obicere
 */
public class Method extends BytecodeElement {

    private final int accessFlags;

    private final int nameIndex;

    private final int descriptorIndex;

    private final Attribute[] attributes;

    private final AttributeSet attributeSet;

    public Method(final int accessFlags, final int nameIndex, final int descriptorIndex, final Attribute[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
        this.attributeSet = new AttributeSet(attributes);
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
        }

        final CodeAttribute code = attributeSet.getAttribute(CodeAttribute.class);
        final boolean hasBody = (code != null);

        modelAnnotations(builder);
        modelDeclaration(builder, hasBody);

        if (hasBody) {

            // allow methods to be collapsed
            builder.indent();
            builder.openCollapsibleBlock();
            code.model(builder);
            builder.unindent();
            builder.closeCollapsibleBlock();

            builder.add("}");
        }
    }

    private void addSynthetic(final DocumentBuilder builder) {
        builder.addComment("Synthetic Method");
        builder.newLine();
    }

    private void modelDeclaration(final DocumentBuilder builder, final boolean hasBody) {
        final ConstantPool constantPool = builder.getConstantPool();

        final String[] accessNames = BytecodeUtils.getMethodAccessNames(accessFlags);

        // make sure to add the default flag if the method has a body
        // and its containing class is an interface
        if (hasBody && BytecodeUtils.isInterface(builder.getClassFile().getAccessFlags())) {
            builder.addKeyword("default ");
        }

        for (final String accessName : accessNames) {
            builder.addKeyword(accessName + " ");
        }

        final MethodSignature signature;
        final SignatureAttribute attribute = attributeSet.getAttribute(SignatureAttribute.class);
        if (attribute != null) {
            signature = attribute.parseMethod(constantPool);
        } else {
            final String name = constantPool.getAsString(descriptorIndex);
            signature = SignatureAttribute.parseMethod(name);
        }
        final String methodName = constantPool.getAsString(nameIndex);
        final boolean constructor = methodName.equals("<init>");
        final boolean staticInitializer = methodName.equals("<clinit>");

        signature.modelTypeParameters(builder);

        if (constructor) {
            // instead replace method name "<init>" with the class name
            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(BytecodeUtils.getClassName(builder.getClassFile().getName()));
            } else {
                builder.add(BytecodeUtils.getQualifiedName(builder.getClassFile().getName()));
            }
        } else if (!staticInitializer) {
            // set the name to the method name otherwise - no name for clinit
            signature.modelReturnType(builder);
            builder.add(" " + methodName);
        }

        if (!staticInitializer) {
            addAnnotationsSignature(signature);
            final MethodParametersAttribute parameterAttribute = attributeSet.getAttribute(MethodParametersAttribute.class);
            if (parameterAttribute != null) {
                final Parameter[] parameters = parameterAttribute.getParameters();
                signature.modelParameters(builder, parameters);
            } else {
                // otherwise, model un-named and unknown access parameters
                signature.modelParameters(builder);
            }
            final boolean throwsSet = signature.modelThrowsSignatures(builder);
            final ExceptionsAttribute exceptionsAttribute = attributeSet.getAttribute(ExceptionsAttribute.class);

            if (exceptionsAttribute != null) {
                boolean first = !throwsSet;
                for (final int index : exceptionsAttribute.getIndexTable()) {
                    if (first) {
                        builder.addKeyword(" throws ");
                        first = false;
                    } else {
                        builder.comma();
                    }
                    final String name = constantPool.getAsString(index);
                    builder.add(BytecodeUtils.getQualifiedName(name));
                }
            }
        }

        if (hasBody) {
            builder.add(" {");
        } else {
            modelAbstractClose(builder);
        }
    }

    private void addAnnotationsSignature(final MethodSignature signature) {
        final RuntimeVisibleParameterAnnotationsAttribute rvpa = attributeSet.getAttribute(RuntimeVisibleParameterAnnotationsAttribute.class);
        final RuntimeInvisibleParameterAnnotationsAttribute ripa = attributeSet.getAttribute(RuntimeInvisibleParameterAnnotationsAttribute.class);
        final RuntimeVisibleTypeAnnotationsAttribute rvta = attributeSet.getAttribute(RuntimeVisibleTypeAnnotationsAttribute.class);
        final RuntimeInvisibleTypeAnnotationsAttribute rita = attributeSet.getAttribute(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvpa != null) {
            signature.addAnnotations(rvpa.getParameterAnnotations());
        }
        if (ripa != null) {
            signature.addAnnotations(ripa.getParameterAnnotations());
        }
        if (rvta != null) {
            signature.addAnnotations(rvta.getAnnotations());
        }
        if (rita != null) {
            signature.addAnnotations(rita.getAnnotations());
        }
    }

    private void modelAnnotations(final DocumentBuilder builder) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                e.model(builder);
                builder.newLine();
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                e.model(builder);
                builder.newLine();
            });
        }
    }

    private void modelAbstractClose(final DocumentBuilder builder) {
        final AnnotationDefaultAttribute hasDefault = attributeSet.getAttribute(AnnotationDefaultAttribute.class);
        if (hasDefault != null) {
            builder.addKeyword(" default ");
            hasDefault.getDefaultValue().model(builder);
        }
        builder.add(";");
    }

}
