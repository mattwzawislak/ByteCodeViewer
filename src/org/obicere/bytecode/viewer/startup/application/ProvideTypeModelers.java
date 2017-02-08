package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.*;
import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.common.InnerClass;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationModeler;
import org.obicere.bytecode.viewer.modeler.ClassModeler;
import org.obicere.bytecode.viewer.modeler.FieldModeler;
import org.obicere.bytecode.viewer.modeler.InnerClassModeler;
import org.obicere.bytecode.viewer.modeler.MethodModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideTypeModelers implements StartUpTask {

    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(Annotation.IDENTIFIER, new AnnotationModeler());
        modelerSet.add(DefaultJCClass.IDENTIFIER, new ClassModeler());
        modelerSet.add(DefaultJCField.IDENTIFIER, new FieldModeler());
        modelerSet.add(InnerClass.IDENTIFIER, new InnerClassModeler());
        modelerSet.add(DefaultJCMethod.IDENTIFIER, new MethodModeler());
    }
}
