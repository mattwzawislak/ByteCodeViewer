package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.FrameCodeBlock;
import org.obicere.bytecode.core.objects.LineCodeBlock;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.EmptyModeler;
import org.obicere.bytecode.viewer.modeler.FrameCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideCodeItemModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(FrameCodeBlock.IDENTIFIER, new FrameCodeBlockModeler());
        modelerSet.add(LineCodeBlock.IDENTIFIER, EmptyModeler.getInstance());

        modelerSet.add(LocalVariable.IDENTIFIER, new LocalVariableModeler());
    }
}
