package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.Field;
import org.obicere.bcviewer.bytecode.Method;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.MultiReader;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ClassFileReader implements Reader<ClassFile> {

    private static final String SOURCE_FILE_ATTRIBUTE_NAME                        = "SourceFile";
    private static final String INNER_CLASSES_ATTRIBUTE_NAME                      = "InnerClasses";
    private static final String ENCLOSING_METHOD_ATTRIBUTE_NAME                   = "EnclosingMethod";
    private static final String SOURCE_DEBUG_EXTENSION_ATTRIBUTE_NAME             = "SourceDebug";
    private static final String BOOTSTRAP_METHODS_ATTRIBUTE_NAME                  = "BootstrapMethods";
    private static final String SYNTHETIC_ATTRIBUTE_NAME                          = "Synthetic";
    private static final String DEPRECATED_ATTRIBUTE_NAME                         = "Deprecated";
    private static final String SIGNATURE_ATTRIBUTE_NAME                          = "Signature";
    private static final String RUNTIME_VISIBLE_ANNOTATIONS_ATTRIBUTE_NAME        = "RuntimeVisibleAnnotations";
    private static final String RUNTIME_INVISIBLE_ANNOTATIONS_ATTRIBUTE_NAME      = "RuntimeInvisibleAnnotations";
    private static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME   = "RuntimeVisibleTypeAnnotations";
    private static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME = "RuntimeInvisibleTypeAnnotations";

    private final SourceFileAttributeReader                      sourceFile                      = new SourceFileAttributeReader();
    private final InnerClassesAttributeReader                    innerClasses                    = new InnerClassesAttributeReader();
    private final EnclosingMethodAttributeReader                 enclosingMethod                 = new EnclosingMethodAttributeReader();
    private final SourceDebugExtensionAttributeReader            sourceDebugExtension            = new SourceDebugExtensionAttributeReader();
    private final BootstrapMethodsAttributeReader                bootstrapMethods                = new BootstrapMethodsAttributeReader();
    private final SyntheticAttributeReader                       synthetic                       = new SyntheticAttributeReader();
    private final DeprecatedAttributeReader                      deprecated                      = new DeprecatedAttributeReader();
    private final SignatureAttributeReader                       signature                       = new SignatureAttributeReader();
    private final RuntimeVisibleAnnotationsAttributeReader       runtimeVisibleAnnotations       = new RuntimeVisibleAnnotationsAttributeReader();
    private final RuntimeInvisibleAnnotationsAttributeReader     runtimeInvisibleAnnotations     = new RuntimeInvisibleAnnotationsAttributeReader();
    private final RuntimeVisibleTypeAnnotationsAttributeReader   runtimeVisibleTypeAnnotations   = new RuntimeVisibleTypeAnnotationsAttributeReader();
    private final RuntimeInvisibleTypeAnnotationsAttributeReader runtimeInvisibleTypeAnnotations = new RuntimeInvisibleTypeAnnotationsAttributeReader();

    private final ConstantPoolReader constantPoolReader = new ConstantPoolReader();

    private final FieldReader  fieldReader;
    private final MethodReader methodReader;

    public ClassFileReader() {
        // We share the same instances so any 3rd-party changes and any
        // shared memory will be available. Yay economy!
        this.fieldReader = new FieldReader(synthetic, deprecated, signature, runtimeVisibleAnnotations, runtimeInvisibleAnnotations, runtimeVisibleTypeAnnotations, runtimeInvisibleTypeAnnotations);
        this.methodReader = new MethodReader(synthetic, deprecated, signature, runtimeVisibleAnnotations, runtimeInvisibleAnnotations, runtimeVisibleTypeAnnotations, runtimeInvisibleTypeAnnotations);
    }

    @Override
    public ClassFile read(final IndexedDataInputStream input) throws IOException {
        final int magic = input.readInt();
        if (magic != 0xCAFEBABE) {
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

        // read all the fields

        final int fieldsCount = input.readUnsignedShort();
        final Field[] fields = new Field[fieldsCount];
        for(int i = 0; i < fieldsCount; i++){
            fields[i] = fieldReader.read(input);
        }

        // read all the methods

        final int methodsCount = input.readUnsignedShort();
        final Method[] methods = new Method[methodsCount];
        for(int i = 0; i < methodsCount; i++){
            methods[i] = methodReader.read(input);
        }

        // Can't be declared finalized as the constant pool needs to be
        // defined first to access attribute names
        final ClassFileAttributesReader classFileAttributesReader = new ClassFileAttributesReader(constantPool);

        final int attributesCount = input.readUnsignedShort();
        final Attribute[] attributes = new Attribute[attributesCount];
        for(int i = 0; i < attributesCount; i++){
            attributes[i] = classFileAttributesReader.read(input);
        }

        return new ClassFile(minor, major, constantPool, accessFlags, thisClass, superClass, interfaces, fields, methods, attributes);
    }

    public SourceFileAttributeReader getSourceFileReader() {
        return sourceFile;
    }

    public InnerClassesAttributeReader getInnerClassesReader() {
        return innerClasses;
    }

    public EnclosingMethodAttributeReader getEnclosingMethodReader() {
        return enclosingMethod;
    }

    public SourceDebugExtensionAttributeReader getSourceDebugExtensionReader() {
        return sourceDebugExtension;
    }

    public BootstrapMethodsAttributeReader getBootstrapMethodsReader() {
        return bootstrapMethods;
    }

    public SyntheticAttributeReader getSyntheticReader() {
        return synthetic;
    }

    public SignatureAttributeReader getSignatureReader() {
        return signature;
    }

    public DeprecatedAttributeReader getDeprecatedReader() {
        return deprecated;
    }

    public RuntimeVisibleAnnotationsAttributeReader getRuntimeVisibleAnnotationsReader() {
        return runtimeVisibleAnnotations;
    }

    public RuntimeInvisibleAnnotationsAttributeReader getRuntimeInvisibleAnnotationsReader() {
        return runtimeInvisibleAnnotations;
    }

    public RuntimeVisibleTypeAnnotationsAttributeReader getRuntimeVisibleTypeAnnotationsReader() {
        return runtimeVisibleTypeAnnotations;
    }

    public RuntimeInvisibleTypeAnnotationsAttributeReader getRuntimeInvisibleTypeAnnotationsReader() {
        return runtimeInvisibleTypeAnnotations;
    }

    public ConstantPoolReader getConstantPoolReader() {
        return constantPoolReader;
    }

    public FieldReader getFieldReader(){
        return fieldReader;
    }

    public MethodReader getMethodReader() {
        return methodReader;
    }

    private class ClassFileAttributesReader extends MultiReader<String, Attribute> {

        private final ConstantPool constantPool;

        public ClassFileAttributesReader(final ConstantPool constantPool) {
            this.constantPool = constantPool;

            add(SOURCE_FILE_ATTRIBUTE_NAME, sourceFile);
            add(INNER_CLASSES_ATTRIBUTE_NAME, innerClasses);
            add(ENCLOSING_METHOD_ATTRIBUTE_NAME, enclosingMethod);
            add(SOURCE_DEBUG_EXTENSION_ATTRIBUTE_NAME, sourceDebugExtension);
            add(BOOTSTRAP_METHODS_ATTRIBUTE_NAME, bootstrapMethods);
            add(SYNTHETIC_ATTRIBUTE_NAME, synthetic);
            add(DEPRECATED_ATTRIBUTE_NAME, deprecated);
            add(SIGNATURE_ATTRIBUTE_NAME, signature);
            add(RUNTIME_VISIBLE_ANNOTATIONS_ATTRIBUTE_NAME, runtimeVisibleAnnotations);
            add(RUNTIME_INVISIBLE_ANNOTATIONS_ATTRIBUTE_NAME, runtimeInvisibleAnnotations);
            add(RUNTIME_VISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME, runtimeVisibleTypeAnnotations);
            add(RUNTIME_INVISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME, runtimeInvisibleTypeAnnotations);
        }

        @Override
        public Attribute read(final IndexedDataInputStream input) throws IOException {
            final int attributeNameIndex = input.readUnsignedShort();
            final String attributeName = (String) constantPool.get(attributeNameIndex).get(constantPool);
            final Reader<? extends Attribute> reader = get(attributeName);
            if (reader == null) {
                throw new ClassFormatError("unknown attribute reached and no way to handle it available.");
            }
            return reader.read(input);
        }
    }
}
