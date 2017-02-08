package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.constant.DefaultConstantClass;
import org.obicere.bytecode.core.objects.constant.DefaultConstantDouble;
import org.obicere.bytecode.core.objects.constant.DefaultConstantFieldRef;
import org.obicere.bytecode.core.objects.constant.ConstantFloat;
import org.obicere.bytecode.core.objects.constant.ConstantInteger;
import org.obicere.bytecode.core.objects.constant.ConstantInvokeDynamic;
import org.obicere.bytecode.core.objects.constant.ConstantLong;
import org.obicere.bytecode.core.objects.constant.ConstantMethodHandle;
import org.obicere.bytecode.core.objects.constant.ConstantMethodType;
import org.obicere.bytecode.core.objects.constant.ConstantNameAndType;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.constant.ConstantString;
import org.obicere.bytecode.core.objects.constant.ConstantUtf8;
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
import org.obicere.bytecode.viewer.modeler.ConstantPoolModeler;
import org.obicere.bytecode.viewer.modeler.ConstantStringModeler;
import org.obicere.bytecode.viewer.modeler.ConstantUtf8Modeler;
import org.obicere.bytecode.viewer.modeler.MethodRefModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideConstantModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(ConstantPool.IDENTIFIER, new ConstantPoolModeler());

        modelerSet.add(DefaultConstantClass.IDENTIFIER, new ConstantClassModeler());
        modelerSet.add(DefaultConstantDouble.IDENTIFIER, new ConstantDoubleModeler());
        modelerSet.add(DefaultConstantFieldRef.IDENTIFIER, new ConstantFieldRefModeler());
        modelerSet.add(ConstantFloat.IDENTIFIER, new ConstantFloatModeler());
        modelerSet.add(ConstantInteger.IDENTIFIER, new ConstantIntegerModeler());
        modelerSet.add(ConstantInvokeDynamic.IDENTIFIER, new ConstantInvokeDynamicModeler());
        modelerSet.add(ConstantLong.IDENTIFIER, new ConstantLongModeler());
        modelerSet.add(ConstantMethodHandle.IDENTIFIER, new ConstantMethodHandleModeler());
        modelerSet.add(ConstantMethodType.IDENTIFIER, new ConstantMethodTypeModeler());
        modelerSet.add(ConstantNameAndType.IDENTIFIER, new ConstantNameAndTypeModeler());
        modelerSet.add(ConstantString.IDENTIFIER, new ConstantStringModeler());
        modelerSet.add(ConstantUtf8.IDENTIFIER, new ConstantUtf8Modeler());

        // ConstantMethodRef and ConstantInterfaceMethodRef are identical
        // and can be reused with a single modeler
        // modelerSet.add(ConstantMethodRef.IDENTIFIER, new ConstantMethodRefModeler());
        // modelerSet.add(ConstantInterfaceMethodRef.IDENTIFIER, new ConstantInterfaceMethodRefModeler());
        modelerSet.add(MethodRef.IDENTIFIER, new MethodRefModeler());
    }
}
