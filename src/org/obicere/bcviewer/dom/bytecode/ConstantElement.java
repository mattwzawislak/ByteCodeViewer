package org.obicere.bcviewer.dom.bytecode;

import org.obicere.bcviewer.bytecode.Constant;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 */
public class ConstantElement extends TextElement {

    private static final String UTF8_NAME = "Utf8";

    private static final String INTEGER_NAME = "Integer";

    private static final String FLOAT_NAME = "Float";

    private static final String LONG_NAME = "Long";

    private static final String DOUBLE_NAME = "Double";

    private static final String CLASS_NAME = "Class";

    private static final String STRING_NAME = "String";

    private static final String FIELD_REF_NAME = "FieldRef";

    private static final String METHOD_REF_NAME = "MethodRef";

    private static final String INTERFACE_METHOD_REF_NAME = "InterfaceMethodRef";

    private static final String NAME_AND_TYPE_NAME = "NameAndType";

    private static final String METHOD_HANDLE_NAME = "MethodHandle";

    private static final String METHOD_TYPE_NAME = "MethodType";

    private static final String INVOKE_DYNAMIC_NAME = "InvokeDynamic";

    private static final int MAX_NAME_LENGTH = 18;

    public ConstantElement(final Constant constant, final DocumentBuilder builder) {
        super(constant.getIdentifier());
        // will check the tag validity
        setText(getName(constant.getTag()));
        setLeftPad(builder.getTabSize());
        setRightPad(builder.getTabbedPaddingSize(getText().length(), MAX_NAME_LENGTH));
        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_CONSTANT));
    }

    private String getName(final int tag) {
        switch (tag) {
            case ConstantReader.CONSTANT_UTF8:
                return UTF8_NAME;
            case ConstantReader.CONSTANT_INTEGER:
                return INTEGER_NAME;
            case ConstantReader.CONSTANT_FLOAT:
                return FLOAT_NAME;
            case ConstantReader.CONSTANT_LONG:
                return LONG_NAME;
            case ConstantReader.CONSTANT_DOUBLE:
                return DOUBLE_NAME;
            case ConstantReader.CONSTANT_CLASS:
                return CLASS_NAME;
            case ConstantReader.CONSTANT_STRING:
                return STRING_NAME;
            case ConstantReader.CONSTANT_FIELD_REF:
                return FIELD_REF_NAME;
            case ConstantReader.CONSTANT_METHOD_REF:
                return METHOD_REF_NAME;
            case ConstantReader.CONSTANT_INTERFACE_METHOD_REF:
                return INTERFACE_METHOD_REF_NAME;
            case ConstantReader.CONSTANT_NAME_AND_TYPE:
                return NAME_AND_TYPE_NAME;
            case ConstantReader.CONSTANT_METHOD_HANDLE:
                return METHOD_HANDLE_NAME;
            case ConstantReader.CONSTANT_METHOD_TYPE:
                return METHOD_TYPE_NAME;
            case ConstantReader.CONSTANT_INVOKE_DYNAMIC:
                return INVOKE_DYNAMIC_NAME;
            default:
                throw new IllegalArgumentException("illegal tag for constant: " + tag);
        }
    }
}
