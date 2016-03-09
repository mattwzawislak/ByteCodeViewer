package org.obicere.bytecode.viewer.io;

import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class ZipFileSource extends Source {

    private static final int BUFFER_SIZE = 1024; // 8 KiB

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

        zipFile.close();
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
            Logger.getGlobal().log(Level.FINE, "File existence check failed:");
            Logger.getGlobal().log(Level.FINE, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String getPath() {
        return file + "!" + name;
    }

    @Override
    public Source getSibling(final String fileName) {
        try {
            final ZipFile zipFile = new ZipFile(file);
            final String parent = FileUtils.getParentName(name);
            if (parent == null || parent.equals("")) {
                Logger.getGlobal().log(Level.FINE, "Parent could not be resolved");
                return null;
            }
            final String sibling = parent + fileName;
            final ZipEntry entry = zipFile.getEntry(sibling);

            if (entry != null) {
                return new ZipFileSource(file, sibling);
            } else {
                Logger.getGlobal().log(Level.FINE, "Could not resolve file: " + sibling);
                return null;
            }
        } catch (final IOException e) {
            Logger.getGlobal().log(Level.FINE, "Could not resolve sibling from ZipFileSource:");
            Logger.getGlobal().log(Level.FINE, e.getMessage(), e);
            return null;
        }
    }
}
