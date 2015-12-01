package org.obicere.bytecode.viewer.dom;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class PaddingCache {

    private final Map<Integer, SoftReference<String>> usedPads = new HashMap<>();

    private static final PaddingCache INSTANCE = new PaddingCache();

    public static PaddingCache getPaddingCache() {
        return INSTANCE;
    }

    public String getPadding(final int count) {
        final SoftReference<String> padReference = usedPads.get(count);
        if (padReference != null) {
            final String pad = padReference.get();
            if (pad != null) {
                return pad;
            }
        }
        final String newPad = buildPad(count);
        final SoftReference<String> newReference = new SoftReference<>(newPad);
        usedPads.put(count, newReference);
        return newPad;
    }

    private String buildPad(final int count) {
        final char[] pad = new char[count];
        for (int i = 0; i < count; i++) {
            pad[i] = ' ';
        }
        return new String(pad);
    }

}
