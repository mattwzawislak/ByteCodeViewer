package org.obicere.bytecode.viewer.io;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Obicere
 */
public class ZipSystem {

    private final Map<String, TaskedZipFile> files = new ConcurrentHashMap<>();

    public void addZip(final String name, final TaskedZipFile file) {
        final TaskedZipFile oldFile = files.put(name, file);

        try {
            if (oldFile != null) {
                oldFile.closeForcefully();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public TaskedZipFile getZipFile(final String name) throws IOException {
        final TaskedZipFile file = files.get(name);
        if (file != null && !file.isClosed()) {
            return file;
        }

        final TaskedZipFile newFile = new TaskedZipFile(name);
        files.put(name, newFile);

        return newFile;
    }

    public Set<String> clean() {
        final Set<String> removed = new HashSet<>();
        for (final Map.Entry<String, TaskedZipFile> entry : files.entrySet()) {
            final TaskedZipFile file = entry.getValue();
            if (file == null || file.isClosed()) {
                final String key = entry.getKey();
                files.remove(key);
                removed.add(key);
            }
        }
        return removed;
    }
}
