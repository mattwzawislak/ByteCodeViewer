package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.ConstantClass;
import org.obicere.bytecode.core.objects.ConstantDouble;
import org.obicere.bytecode.core.objects.ConstantFieldRef;
import org.obicere.bytecode.core.objects.ConstantFloat;
import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.core.objects.ConstantInvokeDynamic;
import org.obicere.bytecode.core.objects.ConstantLong;
import org.obicere.bytecode.core.objects.ConstantMethodHandle;
import org.obicere.bytecode.core.objects.ConstantMethodType;
import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.MethodRef;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.ConstantClassModeler;
import org.obicere.bytecode.viewer.modeler.ConstantDoubleModeler;
import org.obicere.bytecode.viewer.modeler.ConstantFieldRefModeler;
import org.obicere.bytecode.viewer.modeler.ConstantFloatModeler;
import org.obicere.bytecode.viewer.modeler.ConstantIntegerModeler;
import org.obicere.bytecode.viewer.modeler.ConstantInvokeDynamicModeler;
import org.obicere.bytecode.viewer.modeler.ConstantLongModeler;
import org.obicere.bytecode.viewer.modeler.ConstantMethodHandleModeler;
import org.obicere.bytecode.viewer.modeler.ConstantMethodTypeModeler;
import org.obicere.bytecode.viewer.modeler.ConstantNameAndTypeModeler;
import org.obicere.bytecode.viewer.modeler.MethodRefModeler;
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
        modelerSet.add(ConstantInvokeDynamic.IDENTIFIER, new ConstantInvokeDynamicModeler());
        modelerSet.add(ConstantLong.IDENTIFIER, new ConstantLongModeler());
        modelerSet.add(ConstantMethodHandle.IDENTIFIER, new ConstantMethodHandleModeler());
        modelerSet.add(ConstantMethodType.IDENTIFIER, new ConstantMethodTypeModeler());
        modelerSet.add(ConstantNameAndType.IDENTIFIER, new ConstantNameAndTypeModeler());

        // ConstantMethodRef and ConstantInterfaceMethodRef are identical
        // and can be reused with a single modeler
        // modelerSet.add(ConstantMethodRef.IDENTIFIER, new ConstantMethodRefModeler());
        // modelerSet.add(ConstantInterfaceMethodRef.IDENTIFIER, new ConstantInterfaceMethodRefModeler());
        modelerSet.add(MethodRef.IDENTIFIER, new MethodRefModeler());
    }
}