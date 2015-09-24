package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.ClassSignature;
import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.EmptyTextElement;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.CommentElement;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

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
    // TODO: BootstrapMethods
    // TODO: RuntimeVisibleTypeAnnotations
    // TODO: RuntimeInvisibleTypeAnnotations

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
        } else {
            accessFlags = getAccessFlags();
        }

        final Element classElement = new BasicElement("class");

        constantPool.model(builder, classElement);

        classElement.add(new EmptyTextElement(builder));

        modelVersion(builder, classElement);

        classElement.add(new EmptyTextElement(builder));

        if (BytecodeUtils.isSynthetic(accessFlags)) {
            addSynthetic(builder, classElement);
        } else {
            final Set<SyntheticAttribute> syntheticAttributes = attributeSet.getAttributes(SyntheticAttribute.class);
            if (syntheticAttributes != null && !syntheticAttributes.isEmpty()) {
                // there is at least 1 synthetic - we should set it
                addSynthetic(builder, classElement);
            }
        }

        modelAnnotations(builder, classElement);
        modelClassDeclaration(builder, classElement, accessFlags);

        final TextElement classContent = new EmptyTextElement(builder);
        classContent.setLeftPad(builder.getTabSize());

        modelFields(builder, classContent);
        modelMethods(builder, classContent);
        modelInnerClasses(builder, classContent);

        classElement.add(classContent);
        classElement.add(new PlainElement("close", "}", builder));

        parent.add(classElement);
    }

    private void addSynthetic(final DocumentBuilder builder, final Element parent) {
        parent.add(new CommentElement("synthetic", "Synthetic Class", builder));
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

    private void modelVersion(final DocumentBuilder builder, final Element parent) {

        final Element version = new BasicElement("version", Element.AXIS_LINE);
        version.add(new PlainElement("major", "Major: ", builder));
        version.add(new IntegerElement("majorVersion", majorVersion, builder));
        version.add(new PlainElement("minor", " Minor: ", builder));
        version.add(new IntegerElement("minorVersion", minorVersion, builder));

        parent.add(version);
    }

    private void modelClassDeclaration(final DocumentBuilder builder, final Element parent, final int accessFlags) {
        final Element declaration = new BasicElement("declaration", Element.AXIS_LINE);

        final String[] names = BytecodeUtils.getClassAccessNames(accessFlags);

        for (final String name : names) {
            final TextElement next = new KeywordElement(name, name, builder);
            next.setRightPad(1); // 1 space on right
            declaration.add(next);
        }

        declaration.add(new PlainElement("name", BytecodeUtils.getQualifiedName(getName()), builder));

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

        signature.model(builder, declaration);

        final PlainElement element = new PlainElement("open", "{", builder);
        element.setLeftPad(1);
        declaration.add(element);

        parent.add(declaration);
    }

    private void modelFields(final DocumentBuilder builder, final Element parent) {
        final Field[] fields = getFields();
        if (fields.length == 0) {
            return;
        }
        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];
            final BasicElement nextField = new BasicElement("field" + i);
            field.model(builder, nextField);

            parent.add(nextField);
            parent.add(new EmptyTextElement(builder));
        }
    }

    private void modelMethods(final DocumentBuilder builder, final Element parent) {

    }

    private void modelInnerClasses(final DocumentBuilder builder, final Element parent) {
        for (final Attribute attribute : attributes) {
            if (attribute instanceof InnerClassesAttribute) {
                final InnerClass[] innerClasses = ((InnerClassesAttribute) attribute).getInnerClasses();
                for (final InnerClass innerClass : innerClasses) {
                    final String name = constantPool.getAsString(innerClass.getInnerClassInfoIndex());
                    final String outer = constantPool.getAsString(innerClass.getOuterClassInfoIndex());

                    if (name.equals(getName())) {
                        continue;
                    }
                    if (!outer.equals("<null entry>") && !getName().equals(outer) || "java/lang/invoke/MethodHandles$Lookup".equals(name)) {
                        continue;
                    }

                    parent.add(new EmptyTextElement(builder));
                    final BasicElement element = new BasicElement(innerClass.getIdentifier());
                    parent.add(element);

                    innerClass.model(builder, element);
                }
            }
        }
    }
}
