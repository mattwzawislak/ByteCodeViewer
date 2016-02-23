package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.ConstantValueAttribute;
import org.obicere.bytecode.core.objects.DeprecatedAttribute;
import org.obicere.bytecode.core.objects.EnclosingMethodAttribute;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.objects.LineNumberTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableTypeTableAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.SourceDebugExtensionAttribute;
import org.obicere.bytecode.core.objects.SourceFileAttribute;
import org.obicere.bytecode.core.objects.StackMapTableAttribute;
import org.obicere.bytecode.core.objects.SyntheticAttribute;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationDefaultAttributeModeler;
import org.obicere.bytecode.viewer.modeler.BootstrapMethodsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.CodeAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ConstantValueAttributeModeler;
import org.obicere.bytecode.viewer.modeler.DeprecatedAttributeModeler;
import org.obicere.bytecode.viewer.modeler.EmptyModeler;
import org.obicere.bytecode.viewer.modeler.EnclosingMethodAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.RuntimeInvisibleAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeVisibleAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SignatureAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SourceDebugExtensionAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SourceFileAttributeModeler;
import org.obicere.bytecode.viewer.modeler.StackMapTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SyntheticAttributeModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideAttributeModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(AnnotationDefaultAttribute.IDENTIFIER, new AnnotationDefaultAttributeModeler());
        modelerSet.add(BootstrapMethodsAttribute.IDENTIFIER, new BootstrapMethodsAttributeModeler());
        modelerSet.add(CodeAttribute.IDENTIFIER, new CodeAttributeModeler());
        modelerSet.add(ConstantValueAttribute.IDENTIFIER, new ConstantValueAttributeModeler());
        modelerSet.add(DeprecatedAttribute.IDENTIFIER, new DeprecatedAttributeModeler());
        modelerSet.add(EnclosingMethodAttribute.IDENTIFIER, new EnclosingMethodAttributeModeler());
        modelerSet.add(InnerClassesAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(LineNumberTableAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(LocalVariableTableAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(LocalVariableTypeTableAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(RuntimeInvisibleAnnotationsAttribute.IDENTIFIER, new RuntimeInvisibleAnnotationsAttributeModeler());
        modelerSet.add(RuntimeInvisibleParameterAnnotationsAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(RuntimeInvisibleTypeAnnotationsAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(RuntimeVisibleAnnotationsAttribute.IDENTIFIER, new RuntimeVisibleAnnotationsAttributeModeler());
        modelerSet.add(RuntimeVisibleParameterAnnotationsAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(RuntimeVisibleTypeAnnotationsAttribute.IDENTIFIER, EmptyModeler.getInstance());
        modelerSet.add(SignatureAttribute.IDENTIFIER, new SignatureAttributeModeler());
        modelerSet.add(SourceDebugExtensionAttribute.IDENTIFIER, new SourceDebugExtensionAttributeModeler());
        modelerSet.add(SourceFileAttribute.IDENTIFIER, new SourceFileAttributeModeler());
        modelerSet.add(StackMapTableAttribute.IDENTIFIER, new StackMapTableAttributeModeler());
        modelerSet.add(SyntheticAttribute.IDENTIFIER, new SyntheticAttributeModeler());
    }
}
