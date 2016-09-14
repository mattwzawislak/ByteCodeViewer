package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.code.frame.verification.DoubleVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.FloatVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.IntegerVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.LongVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.NullVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.ObjectVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.TopVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.UninitializedThisVariableInfo;
import org.obicere.bytecode.core.objects.code.frame.verification.UninitializedVariableInfo;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.DoubleVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.FloatVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.IntegerVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.LongVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.NullVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.ObjectVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.TopVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.UninitializedThisVariableInfoModeler;
import org.obicere.bytecode.viewer.modeler.UninitializedVariableInfoModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideVerificationTypeInfoModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(DoubleVariableInfo.IDENTIFIER, new DoubleVariableInfoModeler());
        modelerSet.add(FloatVariableInfo.IDENTIFIER, new FloatVariableInfoModeler());
        modelerSet.add(IntegerVariableInfo.IDENTIFIER, new IntegerVariableInfoModeler());
        modelerSet.add(LongVariableInfo.IDENTIFIER, new LongVariableInfoModeler());
        modelerSet.add(NullVariableInfo.IDENTIFIER, new NullVariableInfoModeler());
        modelerSet.add(ObjectVariableInfo.IDENTIFIER, new ObjectVariableInfoModeler());
        modelerSet.add(TopVariableInfo.IDENTIFIER, new TopVariableInfoModeler());
        modelerSet.add(UninitializedThisVariableInfo.IDENTIFIER, new UninitializedThisVariableInfoModeler());
        modelerSet.add(UninitializedVariableInfo.IDENTIFIER, new UninitializedVariableInfoModeler());
    }
}
