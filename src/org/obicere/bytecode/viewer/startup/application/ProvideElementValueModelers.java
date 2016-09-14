package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.annotation.AnnotationElementValue;
import org.obicere.bytecode.core.objects.annotation.ArrayElementValue;
import org.obicere.bytecode.core.objects.annotation.BooleanElementValue;
import org.obicere.bytecode.core.objects.annotation.ByteElementValue;
import org.obicere.bytecode.core.objects.annotation.CharacterElementValue;
import org.obicere.bytecode.core.objects.annotation.ClassElementValue;
import org.obicere.bytecode.core.objects.annotation.DoubleElementValue;
import org.obicere.bytecode.core.objects.annotation.ElementValuePair;
import org.obicere.bytecode.core.objects.annotation.EnumElementValue;
import org.obicere.bytecode.core.objects.annotation.FloatElementValue;
import org.obicere.bytecode.core.objects.annotation.IntegerElementValue;
import org.obicere.bytecode.core.objects.annotation.LongElementValue;
import org.obicere.bytecode.core.objects.annotation.ShortElementValue;
import org.obicere.bytecode.core.objects.annotation.StringElementValue;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ArrayElementValueModeler;
import org.obicere.bytecode.viewer.modeler.BooleanElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ByteElementValueModeler;
import org.obicere.bytecode.viewer.modeler.CharacterElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ClassElementValueModeler;
import org.obicere.bytecode.viewer.modeler.DoubleElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ElementValuePairModeler;
import org.obicere.bytecode.viewer.modeler.EnumElementValueModeler;
import org.obicere.bytecode.viewer.modeler.FloatElementValueModeler;
import org.obicere.bytecode.viewer.modeler.IntegerElementValueModeler;
import org.obicere.bytecode.viewer.modeler.LongElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.ShortElementValueModeler;
import org.obicere.bytecode.viewer.modeler.StringElementValueModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideElementValueModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(ElementValuePair.IDENTIFIER, new ElementValuePairModeler());

        modelerSet.add(AnnotationElementValue.IDENTIFIER, new AnnotationElementValueModeler());
        modelerSet.add(ArrayElementValue.IDENTIFIER, new ArrayElementValueModeler());
        modelerSet.add(BooleanElementValue.IDENTIFIER, new BooleanElementValueModeler());
        modelerSet.add(ByteElementValue.IDENTIFIER, new ByteElementValueModeler());
        modelerSet.add(CharacterElementValue.IDENTIFIER, new CharacterElementValueModeler());
        modelerSet.add(ClassElementValue.IDENTIFIER, new ClassElementValueModeler());
        modelerSet.add(DoubleElementValue.IDENTIFIER, new DoubleElementValueModeler());
        modelerSet.add(EnumElementValue.IDENTIFIER, new EnumElementValueModeler());
        modelerSet.add(FloatElementValue.IDENTIFIER, new FloatElementValueModeler());
        modelerSet.add(IntegerElementValue.IDENTIFIER, new IntegerElementValueModeler());
        modelerSet.add(LongElementValue.IDENTIFIER, new LongElementValueModeler());
        modelerSet.add(ShortElementValue.IDENTIFIER, new ShortElementValueModeler());
        modelerSet.add(StringElementValue.IDENTIFIER, new StringElementValueModeler());
    }
}
