package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class InnerClassModeler implements Modeler<InnerClass> {
    @Override
    public void model(final InnerClass element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int innerClassInfoIndex = element.getInnerClassInfoIndex();
        final int innerClassAccessFlags = element.getInnerClassAccessFlags();

        final String workingName = builder.getClassFile().getName();
        final String name = constantPool.getAsString(innerClassInfoIndex);

        final String modelledProperty = workingName + "/" + name + ".modelled";
        if (builder.getProperty(modelledProperty) != null) {
            Logger.getGlobal().log(Level.INFO, "Already modeled class: " + workingName + "/" + name);
            return;
        }
        builder.setProperty("accessFlags", innerClassAccessFlags);

        final ClassInformation classInformation = builder.getDomain().getClassStorage().getClass(name);
        if (classInformation == null) {
            Logger.getGlobal().log(Level.INFO, "Class file not loaded: " + name);
            builder.addComment("Class file not loaded: " + name);
            return;
        }

        final ClassFile file = classInformation.getRootClass();

        if (file == null) {
            builder.addComment("Could not find inner class: " + name);
            return;
        }
        builder.setProperty(modelledProperty, "true");
        builder.setWorkingClass(file);
        builder.model(file);
        builder.clearWorkingClass();
    }
}
