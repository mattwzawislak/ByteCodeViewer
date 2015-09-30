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
    public void model(final BytecodeDocumentBuilder builder) {
        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
        } else {
            accessFlags = getAccessFlags();
        }

        builder.openBlock();

        modelVersion(builder);
        builder.newLine();

        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
        }

        modelAnnotations(builder);
        modelClassDeclaration(builder, accessFlags);

        builder.indent();

        modelFields(builder);
        modelMethods(builder);
        modelBootstrapMethods(builder);
        modelInnerClasses(builder);

        builder.unindent();

        builder.newLine();
        builder.add("}");
        builder.newLine();
        builder.closeBlock();
    }

    private void addSynthetic(final BytecodeDocumentBuilder builder) {
        builder.addComment("Synthetic Class");
        builder.newLine();
    }

    private void modelAnnotations(final BytecodeDocumentBuilder builder) {
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

    private void modelVersion(final BytecodeDocumentBuilder builder) {
        builder.add("Major: ");
        builder.add(majorVersion);
        builder.add(" Minor: ");
        builder.add(minorVersion);
        builder.newLine();
    }

    private void modelClassDeclaration(final BytecodeDocumentBuilder builder, final int accessFlags) {

        final String[] names = BytecodeUtils.getClassAccessNames(accessFlags);

        for (final String name : names) {
            builder.addKeyword(name);
            builder.pad(1);
        }

        builder.add(BytecodeUtils.getQualifiedName(getName()));

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

        signature.model(builder);

        builder.add(" {");
    }

    private void modelFields(final BytecodeDocumentBuilder builder) {
        final Field[] fields = getFields();
        if (fields.length == 0) {
            return;
        }
        int current = 1;
        for (final Field field : fields) {
            builder.newLine();
            builder.newLine();

            System.out.print("\rModelling field: (" + current + " / " + fields.length + ")\t");

            field.model(builder);
            current++;
        }
        System.out.println();
    }

    private void modelMethods(final BytecodeDocumentBuilder builder) {
        final Method[] methods = getMethods();
        if (methods.length == 0) {
            return;
        }
        int current = 1;
        for (final Method method : methods) {
            builder.newLine();
            builder.newLine();

            System.out.print("\rModelling method: (" + current + " / " + methods.length + ")\t");
            method.model(builder);
            current++;
        }
        System.out.println();
    }

    private void modelInnerClasses(final BytecodeDocumentBuilder builder) {
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

                builder.newLine();
                builder.newLine();
                innerClass.model(builder);
            }
        }
    }

    private void modelBootstrapMethods(final BytecodeDocumentBuilder builder) {

        final BootstrapMethodsAttribute attribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        if (attribute == null) {
            return;
        }
        attribute.model(builder);
    }
}
