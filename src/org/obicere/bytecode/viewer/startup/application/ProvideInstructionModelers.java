package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.instruction.aaload;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.instruction.aaloadModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideInstructionModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(aaload.IDENTIFIER, new aaloadModeler());
    }
}
