package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.ClassSignature;
import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.CommentElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
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

    // TODO: Code - probably one of the last ones
    // it now is the last one :(

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder, parent);
        }

        final CodeAttribute code = attributeSet.getAttribute(CodeAttribute.class);
        final boolean hasBody = (code != null);

        modelAnnotations(builder, parent);
        modelDeclaration(builder, parent, hasBody);

        if (hasBody) {
            parent.add(new PlainElement("close", "}", builder));
        }
    }

    private void addSynthetic(final DocumentBuilder builder, final Element parent) {
        parent.add(new CommentElement("synthetic", "Synthetic Method", builder));
    }

    private void modelDeclaration(final DocumentBuilder builder, final Element parent, final boolean hasBody) {
        final ConstantPool constantPool = builder.getConstantPool();

        final BasicElement declaration = new BasicElement("declaration", Element.AXIS_LINE);
        final String[] accessNames = BytecodeUtils.getMethodAccessNames(accessFlags);

        // make sure to add the default flag if the method has a body
        // and its containing class is an interface
        if (hasBody && BytecodeUtils.isInterface(builder.getClassFile().getAccessFlags())) {
            final KeywordElement access = new KeywordElement("default", "default", builder);
            access.setRightPad(1);
            declaration.add(access);
        }

        for (final String accessName : accessNames) {
            final KeywordElement access = new KeywordElement(accessName, accessName, builder);
            access.setRightPad(1);
            declaration.add(access);
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

        signature.modelTypeParameters(builder, declaration);

        if (constructor) {
            // instead replace method name "<init>" with the class name
            declaration.add(new PlainElement("name", BytecodeUtils.getQualifiedName(builder.getClassFile().getName()), builder));
        } else if (!staticInitializer) {
            // set the name to the method name otherwise - no name for clinit
            signature.modelReturnType(builder, declaration);
            declaration.add(new PlainElement("name", methodName, builder));
        }

        if (!staticInitializer) {
            addAnnotationsSignature(signature);
            final MethodParametersAttribute parameterAttribute = attributeSet.getAttribute(MethodParametersAttribute.class);
            if (parameterAttribute != null) {
                final Parameter[] parameters = parameterAttribute.getParameters();
                signature.modelParameters(builder, declaration, parameters);
            } else {
                // otherwise, model un-named and unknown access parameters
                signature.modelParameters(builder, declaration);
            }
            final boolean throwsSet = signature.modelThrowsSignatures(builder, declaration);
            final ExceptionsAttribute exceptionsAttribute = attributeSet.getAttribute(ExceptionsAttribute.class);

            if (exceptionsAttribute != null) {
                boolean first = !throwsSet;
                for (final int index : exceptionsAttribute.getIndexTable()) {
                    if (first) {
                        final KeywordElement keyword = new KeywordElement("throws", "throws", builder);
                        keyword.setLeftPad(1);
                        keyword.setRightPad(1);
                        declaration.add(keyword);
                        first = false;
                    } else {
                        final PlainElement comma = new PlainElement("comma", ",", builder);
                        comma.setRightPad(1);
                        declaration.add(comma);
                    }
                    final String name = constantPool.getAsString(index);
                    declaration.add(new PlainElement("throws", BytecodeUtils.getQualifiedName(name), builder));
                }
            }
        }

        if (hasBody) {
            final PlainElement element = new PlainElement("open", "{", builder);
            element.setLeftPad(1);
            declaration.add(element);
        } else {
            modelAbstractClose(builder, declaration);
        }
        parent.add(declaration);
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

    private void modelAnnotations(final DocumentBuilder builder, final Element parent) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> e.model(builder, parent));
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> e.model(builder, parent));
        }
    }

    private void modelAbstractClose(final DocumentBuilder builder, final Element parent) {
        final AnnotationDefaultAttribute hasDefault = attributeSet.getAttribute(AnnotationDefaultAttribute.class);
        if (hasDefault != null) {
            final KeywordElement defaultKeyword = new KeywordElement("default", "default", builder);
            defaultKeyword.setLeftPad(1);
            defaultKeyword.setRightPad(1);
            parent.add(defaultKeyword);
            hasDefault.getDefaultValue().model(builder, parent);
        }
        parent.add(new PlainElement("semicolon", ";", builder));
    }

}
