package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.Field;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.core.objects.Method;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationModeler;
import org.obicere.bytecode.viewer.modeler.BootstrapMethodModeler;
import org.obicere.bytecode.viewer.modeler.ClassFileModeler;
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
        modelerSet.add(BootstrapMethod.IDENTIFIER, new BootstrapMethodModeler());
        modelerSet.add(ClassFile.IDENTIFIER, new ClassFileModeler());
        modelerSet.add(Field.IDENTIFIER, new FieldModeler());
        modelerSet.add(InnerClass.IDENTIFIER, new InnerClassModeler());
        modelerSet.add(Method.IDENTIFIER, new MethodModeler());
    }
}
