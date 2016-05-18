package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.CodeException;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class CodeExceptionModeler implements Modeler<CodeException> {
    @Override
    public void model(final CodeException element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode", false);

        final CodeAttribute code = (CodeAttribute) builder.getProperty("code");

        final String start = code.getBlockName(element.getStartPC());
        final String end = code.getBlockName(element.getEndPC());
        final String handler = code.getBlockName(element.getHandlerPC());

        builder.add(start);
        builder.add(",");
        builder.tab();
        builder.add(end);
        builder.add(",");
        builder.tab();
        builder.add(handler);
        builder.add(",");
        builder.tab();

        final String catchType;
        final int catchTypeValue = element.getCatchType();
        if (catchTypeValue == 0) {
            // 0 catches all exceptions
            if (importMode) {
                catchType = "Throwable";
            } else {
                catchType = "java.lang.Throwable";
            }
        } else {
            catchType = constantPool.getAsString(catchTypeValue);
        }

        if (importMode) {
            builder.add(ByteCodeUtils.getClassName(catchType));
        } else {
            builder.add(ByteCodeUtils.getQualifiedName(catchType));
        }
    }
}
