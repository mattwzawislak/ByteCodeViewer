package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ConstantValueAttribute;
import org.obicere.bytecode.core.objects.Field;
import org.obicere.bytecode.core.objects.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.SyntheticAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Set;

/**
 */
public class FieldModeler implements Modeler<Field> {

    @Override
    public void model(final Field element, final DocumentBuilder builder) {

        modelSynthetic(element, builder);
        modelAnnotations(element, builder);
        modelDeclaration(element, builder);
    }

    private void modelSynthetic(final Field field, final DocumentBuilder builder) {
        final AttributeSet attributeSet = field.getAttributeSet();
        final SyntheticAttribute syntheticAttribute = attributeSet.getAttribute(SyntheticAttribute.class);

        if (syntheticAttribute != null) {
            builder.model(syntheticAttribute);
        } else if (ByteCodeUtils.isSynthetic(field.getAccessFlags())) {
            builder.model(new SyntheticAttribute(0));
        }
    }

    private void modelAnnotations(final Field element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                builder.model(e);
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

    private void modelDeclaration(final Field element, final DocumentBuilder builder) {
        final int accessFlags = element.getAccessFlags();
        final int nameIndex = element.getNameIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final String[] names = ByteCodeUtils.getFieldAccessNames(accessFlags);

        for (final String name : names) {
            builder.addKeyword(name);
            builder.add(" ");
        }

        modelType(element, builder);

        builder.add(" ");
        builder.add(constantPool.getAsString(nameIndex));

        final AttributeSet attributeSet = element.getAttributeSet();
        final ConstantValueAttribute constantAttribute = attributeSet.getAttribute(ConstantValueAttribute.class);
        if (constantAttribute != null) {
            builder.model(constantAttribute);
        }
        builder.add(";");
    }

    private void modelType(final Field element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final AttributeSet attributeSet = element.getAttributeSet();
        final SignatureAttribute attribute = attributeSet.getAttribute(SignatureAttribute.class);

        final FieldSignature signature;
        if (attribute != null) {
            signature = attribute.parseField(constantPool);
        } else {
            final int descriptorIndex = element.getDescriptorIndex();
            final String descriptor = constantPool.getAsString(descriptorIndex);
            signature = SignatureAttribute.parseField(descriptor);
        }

        if (signature != null) {
            // add type annotations to the signature

            final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributeSet.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
            final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributeSet.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

            if (rvtaAttributes != null) {
                rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
            }
            if (ritaAttributes != null) {
                ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
            }

            builder.model(signature);
        }
    }
}