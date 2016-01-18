package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.ConstantValueAttribute;
import org.obicere.bytecode.core.objects.DeprecatedAttribute;
import org.obicere.bytecode.core.objects.EnclosingMethodAttribute;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.objects.LineNumberTableAttribute;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationDefaultAttributeModeler;
import org.obicere.bytecode.viewer.modeler.BootstrapMethodsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.CodeAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ConstantValueAttributeModeler;
import org.obicere.bytecode.viewer.modeler.DeprecatedAttributeModeler;
import org.obicere.bytecode.viewer.modeler.EnclosingMethodAttributeModeler;
import org.obicere.bytecode.viewer.modeler.InnerClassesAttributeModeler;
import org.obicere.bytecode.viewer.modeler.LineNumberTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideAttributeModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(AnnotationDefaultAttribute.IDENTIFIER, new AnnotationDefaultAttributeModeler());
        modelerSet.add(BootstrapMethodsAttribute.IDENTIFIER, new BootstrapMethodsAttributeModeler());
        modelerSet.add(CodeAttribute.IDENTIFIER, new CodeAttributeModeler());
        modelerSet.add(ConstantValueAttribute.IDENTIFIER, new ConstantValueAttributeModeler());
        modelerSet.add(DeprecatedAttribute.IDENTIFIER, new DeprecatedAttributeModeler());
        modelerSet.add(EnclosingMethodAttribute.IDENTIFIER, new EnclosingMethodAttributeModeler());
        modelerSet.add(InnerClassesAttribute.IDENTIFIER, new InnerClassesAttributeModeler());
        modelerSet.add(LineNumberTableAttribute.IDENTIFIER, new LineNumberTableAttributeModeler());
    }
}
