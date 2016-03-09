package org.obicere.bytecode.viewer.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 */
public class FileSource extends Source {

    private static final int BUFFER_SIZE = 1024; // 1 KiB

    private final String file;

    public FileSource(final String file) {
        this.file = file;
    }

    public FileSource(final File file) {
        this.file = file.getAbsolutePath();
    }

    @Override
    public byte[] read() throws IOException {
        final FileInputStream stream = new FileInputStream(open());
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
        return open().exists();
    }

    @Override
    public String getPath() {
        return file;
    }

    @Override
    public Source getSibling(final String fileName) {
        final File thisFile = open();
        final File parent = thisFile.getParentFile();
        return new FileSource(new File(parent, fileName).getAbsolutePath());
    }

    private File open() {
        return new File(file);
    }
}
