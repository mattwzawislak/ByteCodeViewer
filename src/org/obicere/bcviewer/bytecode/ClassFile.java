package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.EmptyTextElement;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.xml.soap.Text;

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

    // TODO: SourceFile
    // TODO: EnclosingMethod
    // TODO: SourceDebugExtension
    // TODO: BootstrapMethods
    // TODO: Synthetic
    // TODO: Deprecated
    // TODO: RuntimeVisibleAnnotations
    // TODO: RuntimeInvisibleAnnotations
    // TODO: RuntimeVisibleTypeAnnotations
    // TODO: RuntimeInvisibleTypeAnnotations

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final Element classElement = new BasicElement("class");

        constantPool.model(builder, classElement);

        classElement.add(new EmptyTextElement(builder));

        modelVersion(builder, classElement);

        classElement.add(new EmptyTextElement(builder));

        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
        } else {
            accessFlags = getAccessFlags();
        }

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

        boolean modeled = false;
        for (final Attribute attribute : attributes) {
            if (attribute instanceof SignatureAttribute) {
                attribute.model(builder, declaration);
                modeled = true;
                break;
            }
        }

        if (!modeled) {
            final String superName = getSuperName();
            if (!superName.equals("java/lang/Object")) {
                final TextElement extendsKeyword = new KeywordElement("extends", "extends", builder);
                extendsKeyword.setLeftPad(1);
                extendsKeyword.setRightPad(1);
                declaration.add(extendsKeyword);

                final TextElement superClassName = new PlainElement("super", BytecodeUtils.getQualifiedName(superName), builder);
                declaration.add(superClassName);
            }

            final int[] interfaces = getInterfaces();
            if (interfaces.length > 0) {
                final String impOrExt = BytecodeUtils.isInterface(accessFlags) ? "extends" : "implements";
                final TextElement implementsKeyword = new KeywordElement(impOrExt, impOrExt, builder);
                implementsKeyword.setLeftPad(1);
                implementsKeyword.setRightPad(1);
                declaration.add(implementsKeyword);

                for (int i = 0; i < interfaces.length; i++) {
                    final int index = interfaces[i];
                    // make sure to not add comma at last interface
                    final String text = getInterface(index) + (i == interfaces.length - 1 ? "" : ",");
                    final TextElement next = new PlainElement("interface" + index, BytecodeUtils.getQualifiedName(text), builder);
                    declaration.add(next);
                }
            }
        }
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
            final BasicElement nextField = new BasicElement("field" + i, Element.AXIS_LINE);
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

                    if(name.equals(getName())){
                        continue;
                    }
                    // method enclosed classes have outer be a null entry
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
