package org.obicere.bytecode.viewer.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * @author Obicere
 */
public class TaskedZipFile extends ZipFile {

    private final List<Long> tasks = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();

    private boolean closed = false;

    public TaskedZipFile(final String name) throws IOException {
        super(name);
    }

    public TaskedZipFile(final File file, final int mode) throws IOException {
        super(file, mode);
    }

    public TaskedZipFile(final File file) throws ZipException, IOException {
        super(file);
    }

    public TaskedZipFile(final File file, final int mode, final Charset charset) throws IOException {
        super(file, mode, charset);
    }

    public TaskedZipFile(final String name, final Charset charset) throws IOException {
        super(name, charset);
    }

    public TaskedZipFile(final File file, final Charset charset) throws IOException {
        super(file, charset);
    }

    public void addTask(final long task) {
        try {
            lock.lock();

            tasks.add(task);
        } finally {
            lock.unlock();
        }
    }

    public boolean complete() {
        try {
            lock.lock();
            return checkCompleteSafely();
        } finally {
            lock.unlock();
        }
    }

    private boolean checkCompleteSafely() {
        return tasks.isEmpty();
    }

    @Override
    public InputStream getInputStream(final ZipEntry entry) throws IOException {
        final InputStream stream = super.getInputStream(entry);
        final long task = entry.getCrc();

        return new TaskedZipFileInputStream(stream, task);
    }

    public boolean isClosed() {
        try {
            lock.lock();

            return closed;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            lock.lock();
            if (checkCompleteSafely()) {
                super.close();
                this.closed = true;
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Long> closeForcefully() throws IOException {
        try {
            lock.lock();
            super.close();
            this.closed = true;

            return new ArrayList<>(tasks);
        } finally {
            lock.unlock();
        }
    }

    private class TaskedZipFileInputStream extends InputStream {

        private final InputStream stream;

        private final long task;

        public TaskedZipFileInputStream(final InputStream stream, final long task) {
            this.stream = stream;
            this.task = task;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public void close() throws IOException {
            super.close();
            try {
                lock.lock();

                tasks.remove(task);
            } finally {
                lock.unlock();
            }
        }
    }
}
