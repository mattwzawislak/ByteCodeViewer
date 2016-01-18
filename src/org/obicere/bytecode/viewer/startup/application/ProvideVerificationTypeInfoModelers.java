package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.DoubleVariableInfo;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.DoubleVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideVerificationTypeInfoModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(DoubleVariableInfo.IDENTIFIER, new DoubleVariableInfoModeler());
    }
}
