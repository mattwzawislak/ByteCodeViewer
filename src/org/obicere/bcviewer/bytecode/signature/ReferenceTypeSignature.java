package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class ReferenceTypeSignature extends JavaTypeSignature {

    public static ReferenceTypeSignature parse(final QueueString string) {
        final QueueString save = string.save();
        final ReferenceTypeSignature cts = ClassTypeSignature.parse(string);

        if (cts != null) {
            return cts;
        }
        string.restoreTo(save);
        final ReferenceTypeSignature tvs = TypeVariableSignature.parse(string);
        if (tvs != null) {
            return tvs;
        }
        string.restoreTo(save);
        return ArrayTypeSignature.parse(string);
    }

}
