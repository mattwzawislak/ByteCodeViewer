package org.obicere.bytecode.viewer.util.hashing;

/**
 * @author Obicere
 */
public interface HashingAlgorithm {

    public String getName();

    public byte[] hash(final byte[] bytes);

}
