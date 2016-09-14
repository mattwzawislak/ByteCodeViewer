package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.code.frame.AppendFrame;
import org.obicere.bytecode.core.objects.code.frame.ChopFrame;
import org.obicere.bytecode.core.objects.code.frame.FullFrame;
import org.obicere.bytecode.core.objects.code.frame.SameFrame;
import org.obicere.bytecode.core.objects.code.frame.SameFrameExtended;
import org.obicere.bytecode.core.objects.code.frame.SameLocals1StackItemFrame;
import org.obicere.bytecode.core.objects.code.frame.SameLocals1StackItemFrameExtended;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AppendFrameModeler;
import org.obicere.bytecode.viewer.modeler.ChopFrameModeler;
import org.obicere.bytecode.viewer.modeler.FullFrameModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.SameFrameExtendedModeler;
import org.obicere.bytecode.viewer.modeler.SameFrameModeler;
import org.obicere.bytecode.viewer.modeler.SameLocals1StackItemFrameExtendedModeler;
import org.obicere.bytecode.viewer.modeler.SameLocals1StackItemFrameModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideFrameModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(AppendFrame.IDENTIFIER, new AppendFrameModeler());
        modelerSet.add(ChopFrame.IDENTIFIER, new ChopFrameModeler());
        modelerSet.add(FullFrame.IDENTIFIER, new FullFrameModeler());
        modelerSet.add(SameFrame.IDENTIFIER, new SameFrameModeler());
        modelerSet.add(SameFrameExtended.IDENTIFIER, new SameFrameExtendedModeler());
        modelerSet.add(SameLocals1StackItemFrame.IDENTIFIER, new SameLocals1StackItemFrameModeler());
        modelerSet.add(SameLocals1StackItemFrameExtended.IDENTIFIER, new SameLocals1StackItemFrameExtendedModeler());
    }
}
