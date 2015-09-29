package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.ClassSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;
import java.util.Set;

/**
 * @author Obicere
 */
public class ClassFile extends BytecodeElement {

    private final int minorVersion;

    private final int majorVersion;

    private final ConstantPool constantPool;

    private final int accessFlags;

    private final int thisClass;

    private final int superClass;

    private final int[] interfaces;

    private final Field[] fields;

    private final Method[] methods;

    private final Attribute[] attributes;

    private final AttributeSet attributeSet;

    public ClassFile(final int minorVersion, final int majorVersion, final ConstantPool constantPool, final int accessFlags, final int thisClass, final int superClass, final int[] interfaces, final Field[] fields, final Method[] methods, final Attribute[] attributes) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
        this.accessFlags = accessFlags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
        this.attributeSet = new AttributeSet(attributes);
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getSuperClass() {
        return superClass;
    }

    public String getSuperName() {
        return constantPool.getAsString(superClass);
    }

    public int getThisClass() {
        return thisClass;
    }

    public String getName() {
        return constantPool.getAsString(thisClass);
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public String getInterface(final int index) {
        return constantPool.getAsString(index);
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    // EnclosingMethod - Still not fully implemented, the parsing is quite off
    // basically, the information is stored within itself within an inner class
    // attribute

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
        } else {
            accessFlags = getAccessFlags();
        }

        final Element classElement = builder.addBranch(parent);

        builder.pad(parent, 1);
        constantPool.model(builder, classElement);
        builder.newLine(classElement);
        modelVersion(builder, classElement);
        builder.newLine(classElement);

        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder, parent);
        }

        modelAnnotations(builder, classElement);
        modelClassDeclaration(builder, classElement, accessFlags);

        builder.indent();
        modelFields(builder, classElement);
        modelMethods(builder, classElement);
        modelBootstrapMethods(builder, classElement);
        modelInnerClasses(builder, classElement);

        builder.unindent();

        builder.newLine(parent);
        builder.addPlain(parent, "}");
    }

    private void addSynthetic(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addComment(parent, "Synthetic Class");
        builder.newLine(parent);
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

    private void modelVersion(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addPlain(parent, "Major: ");
        builder.add(parent, majorVersion);
        builder.addPlain(parent, " Minor: ");
        builder.add(parent, minorVersion);
        builder.newLine(parent);
    }

    private void modelClassDeclaration(final BytecodeDocumentBuilder builder, final Element parent, final int accessFlags) {

        final String[] names = BytecodeUtils.getClassAccessNames(accessFlags);

        for (final String name : names) {
            builder.addKeyword(parent, name);
            builder.pad(parent, 1);
        }

        builder.addPlain(parent, BytecodeUtils.getQualifiedName(getName()));

        final Set<SignatureAttribute> signatures = attributeSet.getAttributes(SignatureAttribute.class);
        final ClassSignature signature;
        if (signatures != null && !signatures.isEmpty()) {
            final SignatureAttribute attribute = signatures.iterator().next();
            signature = attribute.parseClass(constantPool);
        } else {
            final StringBuilder newSignature = new StringBuilder();
            newSignature.append('L');
            newSignature.append(getSuperName());
            newSignature.append(';');
            final int[] interfaces = getInterfaces();
            for (final int interfaceIndex : interfaces) {
                final String name = constantPool.getAsString(interfaceIndex);
                newSignature.append('L');
                newSignature.append(name);
                newSignature.append(';');
            }
            signature = SignatureAttribute.parseClass(newSignature.toString());
        }

        final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributeSet.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
        final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributeSet.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvtaAttributes != null) {
            rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }
        if (ritaAttributes != null) {
            ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }

        signature.model(builder, parent);

        builder.addPlain(parent, " {");
    }

    private void modelFields(final BytecodeDocumentBuilder builder, final Element parent) {
        final Field[] fields = getFields();
        if (fields.length == 0) {
            return;
        }
        for (final Field field : fields) {
            builder.newLine(parent);
            builder.newLine(parent);

            final Element nextField = builder.addBranch(parent);
            field.model(builder, nextField);
        }
    }

    private void modelMethods(final BytecodeDocumentBuilder builder, final Element parent) {
        final Method[] methods = getMethods();
        if (methods.length == 0) {
            return;
        }
        for (final Method method : methods) {
            builder.newLine(parent);
            builder.newLine(parent);

            final Element nextMethod = builder.addBranch(parent);
            method.model(builder, nextMethod);
        }
    }

    private void modelInnerClasses(final BytecodeDocumentBuilder builder, final Element parent) {
        final Set<InnerClassesAttribute> attributes = attributeSet.getAttributes(InnerClassesAttribute.class);
        if (attributes == null) {
            return;
        }
        for (final InnerClassesAttribute attribute : attributes) {
            final InnerClass[] innerClasses = attribute.getInnerClasses();
            for (final InnerClass innerClass : innerClasses) {
                final String name = constantPool.getAsString(innerClass.getInnerClassInfoIndex());
                final String outer = constantPool.getAsString(innerClass.getOuterClassInfoIndex());

                if (name.equals(getName())) {
                    continue;
                }
                if (!outer.equals("<null entry>") && !getName().equals(outer) || "java/lang/invoke/MethodHandles$Lookup".equals(name)) {
                    continue;
                }

                final Element nextInnerClass = builder.addBranch(parent);

                builder.newLine(parent);
                builder.newLine(parent);
                innerClass.model(builder, nextInnerClass);
            }
        }
    }

    private void modelBootstrapMethods(final BytecodeDocumentBuilder builder, final Element parent) {

        final BootstrapMethodsAttribute attribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        if (attribute == null) {
            return;
        }
        attribute.model(builder, parent);
    }
}
