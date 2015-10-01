package org.obicere.bcviewer.util;

import org.obicere.utility.io.ArchiveFileSource;
import org.obicere.utility.io.BasicFileSource;
import org.obicere.utility.io.FileSource;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class FileUtils {

    private FileUtils() {
        throw new AssertionError("Cannot initialize FileUtils.");
    }

    public static String getFileExtension(final String name) {
        final int index = name.lastIndexOf('.');
        if (index > 0) {
            final int lastSeparator = lastSeparatorIndex(name);
            // +1 to avoid cases such as "/.name"
            if (index > lastSeparator + 1) {
                return name.substring(index);
            }
        }
        return null;
    }

    public static String getFileExtension(final File file) {
        return getFileExtension(file.getAbsolutePath());
    }

    public static int lastSeparatorIndex(final String name) {
        final int forwardSeparator = name.lastIndexOf('/');
        final int backwardSeparator = name.lastIndexOf('\\');
        return Math.max(forwardSeparator, backwardSeparator);
    }

    public static int lastSeparatorIndex(final File file) {
        return lastSeparatorIndex(file.getName());
    }

    public static String getFileName(final String name) {
        final int separator = lastSeparatorIndex(name);
        final int start;
        if (separator >= 0) {
            // + 1 to pass separator
            start = separator + 1;
        } else {
            start = 0;
        }
        final int dot = name.lastIndexOf('.');
        if (dot < 0) {
            return name.substring(start);
        }
        if (dot == start) {
            return name.substring(dot);
        }
        return name.substring(start, dot);
    }

    public static String getFileName(final File file) {
        return getFileName(file.getAbsolutePath());
    }

    public static String getParentName(final String name) {
        final int separator = lastSeparatorIndex(name);
        if (separator < 0) {
            return null;
        } else if (separator == 0) {
            return "";
        } else {
            return name.substring(0, separator + 1);
        }
    }

    public static String getParentName(final File file) {
        return getParentName(file.getAbsolutePath());
    }

    public static FileSource getFileInSameDirectory(final FileSource fileSource, final String otherName) {
        if (fileSource instanceof ArchiveFileSource) {
            final ArchiveFileSource source = (ArchiveFileSource) fileSource;
            final ZipFile file = source.getArchive();
            final ZipEntry entry = source.getEntry();

            final String name = entry.getName();
            final String parent = getParentName(name);

            final ZipEntry otherEntry = file.getEntry(parent + otherName);
            return new ArchiveFileSource(file, otherEntry);
        } else {
            final String path = fileSource.getPath();
            final String parent = getParentName(path);
            return new BasicFileSource(new File(parent, otherName));
        }
    }
}
