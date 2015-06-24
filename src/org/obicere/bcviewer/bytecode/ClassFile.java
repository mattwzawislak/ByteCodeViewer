package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ClassFile {

    private int magic;
    private short minorVersion;
    private short majorVersion;

    private short constantPoolCount;

    private Object constantPool;

    private short accessFlags;

    private short thisClass;

    private short superClass;

    private short interfacesCount;

    private short[] interfaces;

    private short fieldsCount;

    private Object[] fields;

    private short methodsCount;

    private Object[] methods;

    private short attributesCount;

    private Object[] attributes;

}
