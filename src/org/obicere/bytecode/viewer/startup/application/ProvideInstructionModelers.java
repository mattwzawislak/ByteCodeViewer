package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.instruction.aaload;
import org.obicere.bytecode.core.objects.instruction.aastore;
import org.obicere.bytecode.core.objects.instruction.aconst_null;
import org.obicere.bytecode.core.objects.instruction.aload;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.instruction.aaloadModeler;
import org.obicere.bytecode.viewer.modeler.instruction.aastoreModeler;
import org.obicere.bytecode.viewer.modeler.instruction.aconst_nullModeler;
import org.obicere.bytecode.viewer.modeler.instruction.aloadModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideInstructionModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(aaload.IDENTIFIER, new aaloadModeler());
        modelerSet.add(aastore.IDENTIFIER, new aastoreModeler());
        modelerSet.add(aconst_null.IDENTIFIER, new aconst_nullModeler());
        modelerSet.add(aload.IDENTIFIER, new aloadModeler());
    }
}
