package org.obicere.bytecode.viewer.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class ZipFileSource implements Source {

    private static final int BUFFER_SIZE = 1024; // 1 KiB

    private final String file;
    private final String name;

    public ZipFileSource(final String file, final String name) {
        this.file = file;
        this.name = name;
    }

    @Override
    public byte[] read() throws IOException {
        final ZipFile zipFile = new ZipFile(file);
        final ZipEntry entry = zipFile.getEntry(name);

        final InputStream stream = zipFile.getInputStream(entry);
        final ByteArrayOutputStream file = new ByteArrayOutputStream();

        final byte[] buffer = new byte[BUFFER_SIZE];

        while (true) {
            final int read = stream.read(buffer);

            if (read <= 0) {
                break;
            }
            file.write(buffer, 0, read);
        }

        stream.close();
        return file.toByteArray();
    }

    @Override
    public boolean exists() {
        try {
            final ZipFile zipFile = new ZipFile(file);
            final ZipEntry entry = zipFile.getEntry(name);
            zipFile.close();
            return entry != null;
        } catch (final IOException e) {
            return false;
        }
    }

    @Override
    public String getPath() {
        return file + "!" + name;
    }
}
