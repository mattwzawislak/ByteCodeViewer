package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;

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

    public int getThisClass() {
        return thisClass;
    }

    public String getName() {
        return constantPool.getAsString(thisClass);
    }

    public int[] getInterfaces() {
        return interfaces;
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

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final Element classElement = new BasicElement("class", Element.AXIS_PAGE);
        constantPool.model(builder, classElement);
        modelVersion(builder, classElement);

        parent.add(classElement);
    }

    private void modelVersion(final DocumentBuilder builder, final Element parent) {

        final Element version = new BasicElement("version", Element.AXIS_LINE);
        version.add(new ParameterPlainElement("major", "Major: ", builder));
        version.add(new ParameterIntegerElement("majorVersion", majorVersion, builder));
        version.add(new ParameterPlainElement("minor", "Minor: ", builder));
        version.add(new ParameterIntegerElement("minorVersion", minorVersion, builder));

        parent.add(version);
    }
}
