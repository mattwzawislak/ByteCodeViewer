package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class JavaTypeSignature {

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
