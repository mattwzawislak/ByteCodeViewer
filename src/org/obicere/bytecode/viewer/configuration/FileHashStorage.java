package org.obicere.bytecode.viewer.configuration;

import org.obicere.bytecode.viewer.util.hashing.ChecksumHashingAlgorithm;
import org.obicere.bytecode.viewer.util.hashing.HashingAlgorithm;
import org.obicere.bytecode.viewer.util.hashing.MDHashingAlgorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class FileHashStorage {

    private static final String DEFAULT = "MD5";

    private final Set<HashingAlgorithm> algorithms = new HashSet<>();

    private final Map<String, byte[]> cache = new HashMap<>();

    private HashingAlgorithm algorithm;

    private final ReentrantLock lock = new ReentrantLock();

    public FileHashStorage() {
        addAlgorithm(MDHashingAlgorithm.create("MD5"));
        addAlgorithm(MDHashingAlgorithm.create("SHA-1"));
        addAlgorithm(MDHashingAlgorithm.create("SHA-256"));
        addAlgorithm(ChecksumHashingAlgorithm.create("CRC32"));
        addAlgorithm(ChecksumHashingAlgorithm.create("Adler32"));

        setAlgorithm(DEFAULT);
    }

    public void remove(final String sourceName) {
        cache.remove(sourceName);
    }

    public boolean registerIfAbsent(final String sourceName, final byte[] bytes) {
        if (sourceName == null) {
            throw new NullPointerException("source name must be non-null");
        }
        if (bytes == null) {
            throw new NullPointerException("bytes must be non-null");
        }
        final byte[] cached = cache.get(sourceName);
        if (cached != null) {
            return true;
        }
        final byte[] hash = hash(bytes);
        cache.put(sourceName, hash);
        return false;
    }

    public void register(final String sourceName, final byte[] bytes) {
        if (sourceName == null) {
            throw new NullPointerException("source name must be non-null");
        }
        if (bytes == null) {
            throw new NullPointerException("bytes must be non-null");
        }
        final byte[] hash = hash(bytes);

        cache.put(sourceName, hash);
    }

    public boolean existsAs(final String sourceName, final byte[] bytes) {
        if (sourceName == null) {
            throw new NullPointerException("source name must be non-null");
        }
        if (bytes == null) {
            throw new NullPointerException("bytes must be non-null");
        }
        final byte[] cached = cache.get(sourceName);
        if (cached == null) {
            return false;
        }
        final byte[] hash = hash(bytes);
        return Arrays.equals(cached, hash);
    }

    private byte[] hash(final byte[] bytes) {
        try {
            lock.lock();
            return algorithm.hash(bytes);
        } finally {
            lock.unlock();
        }
    }

    public String getAlgorithm() {
        return algorithm.getName();
    }

    public String[] getAvailableAlgorithms() {
        final String[] names = new String[algorithms.size()];
        int i = 0;
        for (final HashingAlgorithm algorithm : algorithms) {
            names[i++] = algorithm.getName();
        }
        return names;
    }

    public void setAlgorithm(final String name) {
        if (name == null) {
            throw new NullPointerException("name must be non-null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("name must be non-empty");
        }

        HashingAlgorithm newAlgorithm = null;
        for (final HashingAlgorithm algorithm : algorithms) {
            if (name.equals(algorithm.getName())) {
                newAlgorithm = algorithm;
                break;
            }
        }
        if (newAlgorithm == null) {
            throw new IllegalArgumentException("no hashing algorithm for name: " + name);
        }
        algorithm = newAlgorithm;
        cache.clear();
    }

    public void addAlgorithm(final HashingAlgorithm algorithm) {
        if (algorithm == null) {
            return;
        }
        algorithms.add(algorithm);
    }
}
