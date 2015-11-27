package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.Field;
import org.obicere.bcviewer.bytecode.Method;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ClassFileReader implements Reader<ClassFile> {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final ConstantPoolReader constantPoolReader = new ConstantPoolReader();

    @Override
    public ClassFile read(final IndexedDataInputStream input) throws IOException {
        final int start = input.getLogicalIndex();
        final int magic = input.readInt();
        if (magic != MAGIC_NUMBER) {
            throw new ClassFormatError("invalid magic number constant: " + magic);
        }

        final int minor = input.readUnsignedShort();
        final int major = input.readUnsignedShort();

        final ConstantPool constantPool = constantPoolReader.read(input);
        final int accessFlags = input.readUnsignedShort();
        final int thisClass = input.readUnsignedShort();
        final int superClass = input.readUnsignedShort();

        // read all the interfaces
        final int interfacesCount = input.readUnsignedShort();
        final int[] interfaces = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaces[i] = input.readUnsignedShort();
        }

        final AttributeReader attributeReader = new AttributeReader(constantPool);

        // read all the fields
        final FieldReader fieldReader = new FieldReader(attributeReader);

        final int fieldsCount = input.readUnsignedShort();
        final Field[] fields = new Field[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            fields[i] = fieldReader.read(input);
        }

        // read all the methods
        final MethodReader methodReader = new MethodReader(attributeReader);

        final int methodsCount = input.readUnsignedShort();
        final Method[] methods = new Method[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            methods[i] = methodReader.read(input);
        }

        // Can't be declared finalized as the constant pool needs to be
        // defined first to access attribute names

        final int attributesCount = input.readUnsignedShort();
        final Attribute[] attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = attributeReader.read(input);
        }

        final ClassFile file = new ClassFile(minor, major, constantPool, accessFlags, thisClass, superClass, interfaces, fields, methods, attributes);

        final int end = input.getLogicalIndex();
        file.setBounds(start, end);

        return file;
    }

    public ConstantPoolReader getConstantPoolReader() {
        return constantPoolReader;
    }
}
