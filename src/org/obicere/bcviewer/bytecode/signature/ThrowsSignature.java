package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class ThrowsSignature {

    public static ThrowsSignature parse(final QueueString string){
        if(!string.hasNext() || string.next() != '^'){
            return null;
        }
        final QueueString save = string.save();
        final ThrowsSignature classThrowsSignature = ClassThrowsSignature.parse(string);
        if(classThrowsSignature != null){
            return classThrowsSignature;
        }
        string.restoreTo(save);
        return TypeThrowsSignature.parse(string);
    }

}
