package org.obicere.bytecode.viewer.util.hashing;

import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author Obicere
 */
public class ChecksumHashingAlgorithm implements HashingAlgorithm {

    private final String   name;
    private final Checksum checksum;

    private ChecksumHashingAlgorithm(final String name, final Checksum checksum) {
        this.name = name;
        this.checksum = checksum;
    }

    public static HashingAlgorithm create(final String name) {
        switch (name.toUpperCase()) {
            case "CRC32":
                return new ChecksumHashingAlgorithm("CRC32", new CRC32());
            case "ADLER32":
                return new ChecksumHashingAlgorithm("Adler32", new Adler32());
            default:
                return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] hash(final byte[] bytes) {
        checksum.update(bytes, 0, bytes.length);

        final long value = checksum.getValue();
        final byte[] output = new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) (value)
        };
        checksum.reset();
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
