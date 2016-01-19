package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ExceptionsAttribute;
import org.obicere.bytecode.core.objects.Method;
import org.obicere.bytecode.core.objects.MethodParametersAttribute;
import org.obicere.bytecode.core.objects.Parameter;
import org.obicere.bytecode.core.objects.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.SyntheticAttribute;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Set;

/**
 */
public class MethodModeler implements Modeler<Method> {

    @Override
    public void model(final Method element, final DocumentBuilder builder) {
        modelSynthetic(element, builder);
        modelAnnotations(element, builder);
        modelAccessFlags(element, builder);
        modelSignature(element, builder);
        modelAbstractClose(element, builder);
        modelCodeBody(element, builder);
    }

    private void modelSynthetic(final Method element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final SyntheticAttribute synthetic = attributeSet.getAttribute(SyntheticAttribute.class);
        if (synthetic != null) {
            builder.model(synthetic);
        } else if (ByteCodeUtils.isSynthetic(element.getAccessFlags())) {
            builder.model(new SyntheticAttribute(0));
        }
        builder.newLine();
    }

    private void modelAccessFlags(final Method element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        final int accessFlags = element.getAccessFlags();
        final String[] accessNames = ByteCodeUtils.getMethodAccessNames(accessFlags);

        // make sure to add the default flag if the method has a body
        // and its containing class is an interface
        final CodeAttribute codeAttribute = attributeSet.getAttribute(CodeAttribute.class);

        // if this method has a body, yet it the containing class is an interface
        if (codeAttribute != null && ByteCodeUtils.isInterface(builder.getClassFile().getAccessFlags())) {
            builder.addKeyword("default ");
        }

        for (final String accessName : accessNames) {
            builder.addKeyword(accessName + " ");
        }
    }

    private void modelSignature(final Method element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final AttributeSet attributeSet = element.getAttributeSet();

        final int nameIndex = element.getNameIndex();
        final String methodName = constantPool.getAsString(nameIndex);
        if (methodName.equals("<clinit>")) {
            // the name 'static' was added through access flags
            // there is no signature to be added
            return;
        }

        final MethodSignature signature;
        final SignatureAttribute attribute = attributeSet.getAttribute(SignatureAttribute.class);
        if (attribute != null) {
            signature = attribute.parseMethod(constantPool);
        } else {
            final int descriptorIndex = element.getDescriptorIndex();
            final String name = constantPool.getAsString(descriptorIndex);
            signature = SignatureAttribute.parseMethod(name);
        }

        // TODO signature refactor
        signature.modelTypeParameters(builder);
        // static initializer has 'static' access flag which is the name
        modelMethodName(element, methodName, signature, builder);
        modelParameters(element, signature, builder);
        modelExceptions(element, signature, builder);
    }

    private void modelMethodName(final Method element, final String methodName, final MethodSignature signature, final DocumentBuilder builder) {

        if (methodName.equals("<init>")) {
            // instead replace method name "<init>" with the class name
            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(ByteCodeUtils.getClassName(builder.getClassFile().getName()));
            } else {
                builder.add(ByteCodeUtils.getQualifiedName(builder.getClassFile().getName()));
            }
        } else {
            // TODO signature refactor
            // set the name to the method name otherwise - no name for clinit
            signature.modelReturnType(builder);
            builder.add(" ");
            builder.add(methodName);
        }
    }

    private void modelParameters(final Method element, final MethodSignature signature, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        addAnnotationsSignature(element, signature);
        final MethodParametersAttribute parameterAttribute = attributeSet.getAttribute(MethodParametersAttribute.class);
        if (parameterAttribute != null) {
            final Parameter[] parameters = parameterAttribute.getParameters();
            // TODO signature refactor
            signature.modelParameters(builder, parameters);
        } else {
            // TODO signature refactor
            // otherwise, model un-named and unknown access parameters
            signature.modelParameters(builder);
        }
    }

    private void modelExceptions(final Method element, final MethodSignature signature, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final ConstantPool constantPool = builder.getConstantPool();

        // TODO signature refactor
        final boolean throwsSet = signature.modelThrowsSignatures(element, builder);
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
                builder.add(ByteCodeUtils.getQualifiedName(name));
            }
        }
    }

    private void addAnnotationsSignature(final Method element, final MethodSignature signature) {
        final AttributeSet attributeSet = element.getAttributeSet();

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

    private void modelAnnotations(final Method element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                builder.newLine();
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                builder.model(e);
                builder.newLine();
            });
        }
    }

    private void modelAbstractClose(final Method element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        final AnnotationDefaultAttribute hasDefault = attributeSet.getAttribute(AnnotationDefaultAttribute.class);
        if (hasDefault != null) {
            builder.model(hasDefault);
        }
        builder.add(";");
    }

    private void modelCodeBody(final Method element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        final CodeAttribute code = attributeSet.getAttribute(CodeAttribute.class);

        if (code != null) {

            builder.add("{");
            builder.indent();
            builder.openCollapsibleBlock();

            builder.model(code);

            builder.unindent();
            builder.closeCollapsibleBlock();
            builder.add("}");
        }
    }
}
