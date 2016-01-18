package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class InnerClassModeler implements Modeler<InnerClass> {
    @Override
    public void model(final InnerClass element, final DocumentBuilder builder) {
        final int innerClassInfoIndex = element.getInnerClassInfoIndex();
        final int innerClassAccessFlags = element.getInnerClassAccessFlags();

        final String name = builder.getConstantPool().getAsString(innerClassInfoIndex);

        final String modelledProperty = name + ".modelled";
        if (builder.getProperty(modelledProperty) != null) {
            Logger.getGlobal().log(Level.INFO, "Cyclic nesting of inner classes with class: " + name);
            return;
        }
        builder.setProperty("accessFlags", innerClassAccessFlags);

        final ClassFile file = builder.getClassInformation().getClass(name);

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
