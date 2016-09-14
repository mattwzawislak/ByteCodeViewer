package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.common.Parameter;
import org.obicere.bytecode.core.objects.signature.JavaTypeSignature;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ParametersModeler implements Modeler<Parameters> {
    @Override
    public void model(final Parameters element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final Parameter[] parameters = element.getParameters();
        final JavaTypeSignature[] signatures = element.getSignatures();

        final boolean addNames = (parameters != null);

        builder.add("(");
        for (int i = 0; i < signatures.length; i++) {
            if (i != 0) {
                builder.comma();
            }
            final JavaTypeSignature signature = signatures[i];

            if (addNames) {
                final Parameter methodParameter = parameters[i];
                final String[] accessNames = ByteCodeUtils.getFieldAccessNames(methodParameter.getAccessFlags());
                final int nameIndex = methodParameter.getNameIndex();
                final String name = constantPool.getAsString(nameIndex);

                for (final String accessName : accessNames) {
                    builder.addKeyword(accessName);
                    builder.pad(1);
                }
                builder.model(signature);
                builder.add(name);
            }  else {
                // only model signature
                builder.model(signature);
            }
        }
        builder.add(")");
    }
}
