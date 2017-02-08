package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.CodeException;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class CodeExceptionModeler implements Modeler<CodeException> {
    @Override
    public void model(final CodeException element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode", false);

        final Label start = element.getStart();
        final Label end = element.getEnd();
        final Label handler = element.getHandler();

        builder.model(start);
        builder.add(",");
        builder.tab();
        builder.model(end);
        builder.add(",");
        builder.tab();
        builder.model(handler);
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
