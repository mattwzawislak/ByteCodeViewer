package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.MultiReader;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class AttributeReader extends MultiReader<String, Attribute> {

    public static final String CONSTANT_VALUE_ATTRIBUTE_NAME                          = "ConstantValue";
    public static final String CODE_ATTRIBUTE_NAME                                    = "Code";
    public static final String EXCEPTIONS_ATTRIBUTE_NAME                              = "Exceptions";
    public static final String SOURCE_FILE_ATTRIBUTE_NAME                             = "SourceFile";
    public static final String LINE_NUMBER_TABLE_ATTRIBUTE_NAME                       = "LineNumberTable";
    public static final String LOCAL_VARIABLE_TABLE_ATTRIBUTE_NAME                    = "LocalVariableTable";
    public static final String INNER_CLASSES_ATTRIBUTE_NAME                           = "InnerClasses";
    public static final String SYNTHETIC_ATTRIBUTE_NAME                               = "Synthetic";
    public static final String DEPRECATED_ATTRIBUTE_NAME                              = "Deprecated";
    public static final String ENCLOSING_METHOD_ATTRIBUTE_NAME                        = "EnclosingMethod";
    public static final String SIGNATURE_ATTRIBUTE_NAME                               = "Signature";
    public static final String SOURCE_DEBUG_EXTENSION_ATTRIBUTE_NAME                  = "SourceDebugExtension";
    public static final String LOCAL_VARIABLE_TYPE_TABLE_ATTRIBUTE_NAME               = "LocalVariableTypeTable";
    public static final String RUNTIME_VISIBLE_ANNOTATIONS_ATTRIBUTE_NAME             = "RuntimeVisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_ANNOTATIONS_ATTRIBUTE_NAME           = "RuntimeInvisibleAnnotations";
    public static final String RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS_ATTRIBUTE_NAME   = "RuntimeVisibleParameterAnnotations";
    public static final String RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS_ATTRIBUTE_NAME = "RuntimeInvisibleParameterAnnotations";
    public static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME        = "RuntimeVisibleTypeAnnotations";
    public static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME      = "RuntimeInvisibleTypeAnnotations";
    public static final String ANNOTATION_DEFAULT_ATTRIBUTE_NAME                      = "AnnotationDefault";
    public static final String STACK_MAP_TABLE_ATTRIBUTE_NAME                         = "StackMapTable";
    public static final String BOOTSTRAP_METHODS_ATTRIBUTE_NAME                       = "BootstrapMethods";
    public static final String METHOD_PARAMETERS_ATTRIBUTE_NAME                       = "MethodParameters";

    private final ConstantPool constantPool;

    public AttributeReader(final ConstantPool constantPool) {
        this.constantPool = constantPool;

        add(CONSTANT_VALUE_ATTRIBUTE_NAME, new ConstantValueAttributeReader());
        add(CODE_ATTRIBUTE_NAME, new CodeAttributeReader(this));
        add(EXCEPTIONS_ATTRIBUTE_NAME, new ExceptionsAttributeReader());
        add(SOURCE_FILE_ATTRIBUTE_NAME, new SourceFileAttributeReader());
        add(LINE_NUMBER_TABLE_ATTRIBUTE_NAME, new LineNumberTableAttributeReader());
        add(LOCAL_VARIABLE_TABLE_ATTRIBUTE_NAME, new LocalVariableTableAttributeReader());
        add(INNER_CLASSES_ATTRIBUTE_NAME, new InnerClassesAttributeReader());
        add(SYNTHETIC_ATTRIBUTE_NAME, new SyntheticAttributeReader());
        add(DEPRECATED_ATTRIBUTE_NAME, new DeprecatedAttributeReader());
        add(ENCLOSING_METHOD_ATTRIBUTE_NAME, new EnclosingMethodAttributeReader());
        add(SIGNATURE_ATTRIBUTE_NAME, new SignatureAttributeReader());
        add(SOURCE_DEBUG_EXTENSION_ATTRIBUTE_NAME, new SourceDebugExtensionAttributeReader());
        add(LOCAL_VARIABLE_TYPE_TABLE_ATTRIBUTE_NAME, new LocalVariableTypeTableAttributeReader());
        add(RUNTIME_VISIBLE_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeVisibleAnnotationsAttributeReader());
        add(RUNTIME_INVISIBLE_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeInvisibleAnnotationsAttributeReader());
        add(RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeVisibleParameterAnnotationsAttributeReader());
        add(RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeInvisibleParameterAnnotationsAttributeReader());
        add(RUNTIME_VISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeVisibleTypeAnnotationsAttributeReader());
        add(RUNTIME_INVISIBLE_TYPE_ANNOTATIONS_ATTRIBUTE_NAME, new RuntimeInvisibleTypeAnnotationsAttributeReader());
        add(ANNOTATION_DEFAULT_ATTRIBUTE_NAME, new AnnotationDefaultAttributeReader());
        add(STACK_MAP_TABLE_ATTRIBUTE_NAME, new StackMapTableAttributeReader());
        add(BOOTSTRAP_METHODS_ATTRIBUTE_NAME, new BootstrapMethodsAttributeReader());
        add(METHOD_PARAMETERS_ATTRIBUTE_NAME, new MethodParametersAttributeReader());

    }

    @Override
    public Attribute read(final IndexedDataInputStream input) throws IOException {
        final int attributeNameIndex = input.readUnsignedShort();
        input.readInt(); // Read the attribute length. This is ignored generally
        final String attributeName = (String) constantPool.get(attributeNameIndex).get(constantPool);
        final Reader<? implements Attribute> reader = get(attributeName);
        if (reader == null) {
            throw new ClassFormatError("unknown attribute reached and no way to handle it available.");
        }
        return reader.read(input);
    }
}
