package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AppendFrame;
import org.obicere.bytecode.core.objects.ChopFrame;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AppendFrameModeler;
import org.obicere.bytecode.viewer.modeler.ChopFrameModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideFrameModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(AppendFrame.IDENTIFIER, new AppendFrameModeler());
        modelerSet.add(ChopFrame.IDENTIFIER, new ChopFrameModeler());
    }
}
