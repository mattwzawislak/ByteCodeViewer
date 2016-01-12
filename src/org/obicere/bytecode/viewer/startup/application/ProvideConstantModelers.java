package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.ConstantClass;
import org.obicere.bytecode.core.objects.ConstantDouble;
import org.obicere.bytecode.core.objects.ConstantFieldRef;
import org.obicere.bytecode.core.objects.ConstantFloat;
import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.ConstantClassModeler;
import org.obicere.bytecode.viewer.modeler.ConstantDoubleModeler;
import org.obicere.bytecode.viewer.modeler.ConstantFieldRefModeler;
import org.obicere.bytecode.viewer.modeler.ConstantFloatModeler;
import org.obicere.bytecode.viewer.modeler.ConstantIntegerModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideConstantModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(ConstantClass.IDENTIFIER, new ConstantClassModeler());
        modelerSet.add(ConstantDouble.IDENTIFIER, new ConstantDoubleModeler());
        modelerSet.add(ConstantFieldRef.IDENTIFIER, new ConstantFieldRefModeler());
        modelerSet.add(ConstantFloat.IDENTIFIER, new ConstantFloatModeler());
        modelerSet.add(ConstantInteger.IDENTIFIER, new ConstantIntegerModeler());
    }
}
