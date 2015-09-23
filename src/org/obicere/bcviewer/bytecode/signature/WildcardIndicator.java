package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class WildcardIndicator {

    public static WildcardIndicator parse(final QueueString string){
        final QueueString save = string.save();
        final WildcardIndicator ebwi = ExtendsBoundedWildcardIndicator.parse(string);
        if (ebwi != null) {
            return ebwi;
        }
        string.restoreTo(save);
        final WildcardIndicator sbwi = SuperBoundedWildcardIndicator.parse(string);
        if (sbwi != null) {
            return sbwi;
        }
        string.restoreTo(save);

        return UnboundedWildcardIndicator.parse(string);
    }

}
