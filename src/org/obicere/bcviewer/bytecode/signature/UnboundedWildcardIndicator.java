package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class UnboundedWildcardIndicator extends WildcardIndicator {

    public static UnboundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext() && string.next() != '*') {
            return null;
        }
        return new UnboundedWildcardIndicator();
    }

}
