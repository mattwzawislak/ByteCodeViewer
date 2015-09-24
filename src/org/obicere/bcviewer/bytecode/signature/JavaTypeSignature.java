package org.obicere.bcviewer.bytecode.signature;

/**
 */
public abstract class JavaTypeSignature extends AnnotationTarget {

    public static JavaTypeSignature parse(final QueueString string){
        final QueueString save = string.save();

        final ReferenceTypeSignature signature = ReferenceTypeSignature.parse(string);
        if(signature != null){
            return signature;
        }
        string.restoreTo(save);

        return BaseType.parse(string);
    }

}
