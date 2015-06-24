package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ClassFile {

    private int magic;

    private int minorVersion;

    private int majorVersion;

    private Object constantPool;

    private int accessFlags;

    private int thisClass;

    private int superClass;

    private int[] interfaces;

    private Field[] fields;

    private Method[] methods;

    private Attribute[] attributes;

}
