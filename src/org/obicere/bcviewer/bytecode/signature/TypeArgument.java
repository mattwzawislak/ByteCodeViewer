package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class TypeArgument {

    private final WildcardIndicator wildcardIndicator;

    public TypeArgument(final WildcardIndicator wildcardIndicator) {
        this.wildcardIndicator = wildcardIndicator;
    }

    public WildcardIndicator getWildcardIndicator() {
        return wildcardIndicator;
    }

    public static TypeArgument parse(final QueueString string) {
        final WildcardIndicator wildcardIndicator = WildcardIndicator.parse(string);
        if (wildcardIndicator == null) {
            return null;
        }
        return new TypeArgument(wildcardIndicator);
    }

}
