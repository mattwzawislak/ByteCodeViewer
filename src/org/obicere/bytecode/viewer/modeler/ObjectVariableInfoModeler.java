package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ObjectVariableInfo;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ObjectVariableInfoModeler implements Modeler<ObjectVariableInfo> {
    @Override
    public void model(final ObjectVariableInfo element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final String signature = builder.getConstantPool().getAsString(index);
        final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);

        // classes with types need to have these types reflected
        // or this may just be a regular signature
        // they can be parsed as a field signature, bit hacky but whatever
        if (fieldSignature != null) {
            builder.model(fieldSignature);
        } else {
            // otherwise we are dealing with a non-signature descriptor
            // which failed the parser and is just the raw type such as
            // "java.lang.Object"
            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(ByteCodeUtils.getClassName(signature));
            } else {
                builder.add(ByteCodeUtils.getQualifiedName(signature));
            }
        }
    }
}
