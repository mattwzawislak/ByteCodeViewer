package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;
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
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append(constantPool.getAsString(nameIndex));
        builder.append("; ");
        builder.append(constantPool.getAsString(descriptorIndex));
        builder.append('\n');
        builder.append("Access: ");
        for (final String access : BytecodeUtils.getMethodAccessNames(accessFlags)) {
            builder.append(access);
            builder.append(' ');
        }
        builder.append('\n');
        for (final Attribute attribute : attributes) {
            builder.append(attribute.toString(constantPool));
            builder.append('\n');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder, parent);
        }

        final CodeAttribute code = attributeSet.getAttribute(CodeAttribute.class);
        final boolean hasBody = (code != null);

        modelAnnotations(builder, parent);
        modelDeclaration(builder, parent, hasBody);

        if (hasBody) {
            builder.indent();
            builder.newLine(parent);

            code.model(builder, parent);

            builder.unindent();
            builder.newLine(parent);
            builder.addPlain(parent, "}");
        }
    }

    private void addSynthetic(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addComment(parent, "Synthetic Method");
        builder.newLine(parent);
    }

    private void modelDeclaration(final BytecodeDocumentBuilder builder, final Element parent, final boolean hasBody) {
        final ConstantPool constantPool = builder.getConstantPool();

        final String[] accessNames = BytecodeUtils.getMethodAccessNames(accessFlags);

        // make sure to add the default flag if the method has a body
        // and its containing class is an interface
        if (hasBody && BytecodeUtils.isInterface(builder.getClassFile().getAccessFlags())) {
            builder.addKeyword(parent, "default ");
        }

        for (final String accessName : accessNames) {
            builder.addKeyword(parent, accessName + " ");
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

        signature.modelTypeParameters(builder, parent);

        if (constructor) {
            // instead replace method name "<init>" with the class name
            builder.addPlain(parent, BytecodeUtils.getQualifiedName(builder.getClassFile().getName()));
        } else if (!staticInitializer) {
            // set the name to the method name otherwise - no name for clinit
            signature.modelReturnType(builder, parent);
            builder.addPlain(parent, methodName);
        }

        if (!staticInitializer) {
            addAnnotationsSignature(signature);
            final MethodParametersAttribute parameterAttribute = attributeSet.getAttribute(MethodParametersAttribute.class);
            if (parameterAttribute != null) {
                final Parameter[] parameters = parameterAttribute.getParameters();
                signature.modelParameters(builder, parent, parameters);
            } else {
                // otherwise, model un-named and unknown access parameters
                signature.modelParameters(builder, parent);
            }
            final boolean throwsSet = signature.modelThrowsSignatures(builder, parent);
            final ExceptionsAttribute exceptionsAttribute = attributeSet.getAttribute(ExceptionsAttribute.class);

            if (exceptionsAttribute != null) {
                boolean first = !throwsSet;
                for (final int index : exceptionsAttribute.getIndexTable()) {
                    if (first) {
                        builder.addKeyword(parent, " throws ");
                        first = false;
                    } else {
                        builder.comma(parent);
                    }
                    final String name = constantPool.getAsString(index);
                    builder.addPlain(parent, BytecodeUtils.getQualifiedName(name));
                }
            }
        }

        if (hasBody) {
            builder.addPlain(parent, " {");
        } else {
            modelAbstractClose(builder, parent);
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

    private void modelAnnotations(final BytecodeDocumentBuilder builder, final Element parent) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                e.model(builder, parent);
                builder.newLine(parent);
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                e.model(builder, parent);
                builder.newLine(parent);
            });
        }
    }

    private void modelAbstractClose(final BytecodeDocumentBuilder builder, final Element parent) {
        final AnnotationDefaultAttribute hasDefault = attributeSet.getAttribute(AnnotationDefaultAttribute.class);
        if (hasDefault != null) {
            builder.addKeyword(parent, " default ");
            hasDefault.getDefaultValue().model(builder, parent);
        }
        builder.addPlain(parent, ";");
    }

}
