package org.obicere.bytecode.viewer.util.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Obicere
 */
public class MDHashingAlgorithm implements HashingAlgorithm {

    private final String name;

    private final MessageDigest digest;

    private MDHashingAlgorithm(final String name) throws NoSuchAlgorithmException {
        this.name = name;
        this.digest = MessageDigest.getInstance(name);
    }

    public static MDHashingAlgorithm create(final String algorithm) {
        try {
            return new MDHashingAlgorithm(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] hash(final byte[] bytes) {
        final byte[] output = digest.digest(bytes);
        digest.reset();
        return output;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other instanceof HashingAlgorithm) {
            return ((HashingAlgorithm) other).getName().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }
}
