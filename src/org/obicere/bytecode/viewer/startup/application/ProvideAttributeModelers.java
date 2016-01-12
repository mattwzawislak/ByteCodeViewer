package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationDefaultAttributeModeler;
import org.obicere.bytecode.viewer.modeler.BootstrapMethodsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.CodeAttributeModeler;
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
    }
}
