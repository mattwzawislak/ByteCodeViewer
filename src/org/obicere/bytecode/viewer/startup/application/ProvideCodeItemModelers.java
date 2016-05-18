package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.CodeException;
import org.obicere.bytecode.core.objects.FrameCodeBlock;
import org.obicere.bytecode.core.objects.ImplicitCodeBlock;
import org.obicere.bytecode.core.objects.LineCodeBlock;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.CodeExceptionModeler;
import org.obicere.bytecode.viewer.modeler.FrameCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.ImplicitCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.LineCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableTypeModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideCodeItemModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(FrameCodeBlock.IDENTIFIER, new FrameCodeBlockModeler());
        modelerSet.add(ImplicitCodeBlock.IDENTIFIER, new ImplicitCodeBlockModeler());
        modelerSet.add(LineCodeBlock.IDENTIFIER, new LineCodeBlockModeler());

        modelerSet.add(CodeException.IDENTIFIER, new CodeExceptionModeler());
        modelerSet.add(LocalVariable.IDENTIFIER, new LocalVariableModeler());
        modelerSet.add(LocalVariableType.IDENTIFIER, new LocalVariableTypeModeler());
    }
}
