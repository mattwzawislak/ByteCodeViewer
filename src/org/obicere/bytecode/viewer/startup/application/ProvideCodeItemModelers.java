package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.CodeException;
import org.obicere.bytecode.core.objects.FrameCodeBlock;
import org.obicere.bytecode.core.objects.ImplicitCodeBlock;
import org.obicere.bytecode.core.objects.LineCodeBlock;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.core.objects.label.CodeBlockLabel;
import org.obicere.bytecode.core.objects.label.EndLabel;
import org.obicere.bytecode.core.objects.label.LazyLabel;
import org.obicere.bytecode.core.objects.label.OffsetLabel;
import org.obicere.bytecode.core.objects.label.StartLabel;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.CodeExceptionModeler;
import org.obicere.bytecode.viewer.modeler.FrameCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.ImplicitCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.LineCodeBlockModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableTypeModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.label.CodeBlockLabelModeler;
import org.obicere.bytecode.viewer.modeler.label.EndLabelModeler;
import org.obicere.bytecode.viewer.modeler.label.LazyLabelModeler;
import org.obicere.bytecode.viewer.modeler.label.OffsetLabelModeler;
import org.obicere.bytecode.viewer.modeler.label.StartLabelModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideCodeItemModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(CodeBlockLabel.IDENTIFIER, new CodeBlockLabelModeler());
        modelerSet.add(EndLabel.IDENTIFIER, new EndLabelModeler());
        modelerSet.add(LazyLabel.IDENTIFIER, new LazyLabelModeler());
        modelerSet.add(OffsetLabel.IDENTIFIER, new OffsetLabelModeler());
        modelerSet.add(StartLabel.IDENTIFIER, new StartLabelModeler());

        modelerSet.add(FrameCodeBlock.IDENTIFIER, new FrameCodeBlockModeler());
        modelerSet.add(ImplicitCodeBlock.IDENTIFIER, new ImplicitCodeBlockModeler());
        modelerSet.add(LineCodeBlock.IDENTIFIER, new LineCodeBlockModeler());

        modelerSet.add(CodeException.IDENTIFIER, new CodeExceptionModeler());
        modelerSet.add(LocalVariable.IDENTIFIER, new LocalVariableModeler());
        modelerSet.add(LocalVariableType.IDENTIFIER, new LocalVariableTypeModeler());
    }
}
