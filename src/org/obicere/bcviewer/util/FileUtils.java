package org.obicere.bcviewer.util;

import org.obicere.utility.io.ArchiveFileSource;
import org.obicere.utility.io.BasicFileSource;
import org.obicere.utility.io.FileSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Utility for operating with files. This utility is used primarily with
 * the {@link org.obicere.utility.io.FileSource} system. This allows easy
 * location of other files in the relative folder. Ideally the
 * <code>FileSource</code> system would be expanded to include the
 * functionality of this utility.
 * <p>
 * This is currently makeshift and expected to be deprecated within future
 * revisions by providing more functionality in more appropriately placed
 * contexts.
 *
 * @author Obicere
 * @version 0.0
 * @since 0.0
 */
public class FileUtils {

    /**
     * Illegal operation. Cannot be instantiated.
     */
    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * Gets the extension of a file by its absolute path. This includes
     * the <code>.</code> character as the first character. This will not
     * work for extensions that have multiple parts. Only the last part
     * will be returned. This is because of ambiguity with the naming of
     * files. For example:
     * <ul>
     * <li> <code>a1.tar.gz</code>
     * <li> <code>a2.bar.zip</code>
     * </ul>
     * <p>
     * Both of these refer to archives. However, the extension of the
     * first archive is <code>".tar.gz"</code> whereas the extension of
     * the second archive is <code>".zip"</code>. Differentiating between
     * these two cannot be reliably done. Therefore the last part for both
     * of these would be returned: <code>".gz"</code> and
     * <code>".zip"</code> respectively.
     *
     * @param name The file's absolute path.
     * @return The name of the extension including the <code>.</code>
     * character if possible. Otherwise, <code>null</code>.
     */

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


    /**
     * Gets the extension of a file by its absolute path. This includes
     * the <code>.</code> character as the first character. This will not
     * work for extensions that have multiple parts. Only the last part
     * will be returned. This is because of ambiguity with the naming of
     * files. For example:
     * <ul>
     * <li> <code>a1.tar.gz</code>
     * <li> <code>a2.bar.zip</code>
     * </ul>
     * <p>
     * Both of these refer to archives. However, the extension of the
     * first archive is <code>".tar.gz"</code> whereas the extension of
     * the second archive is <code>".zip"</code>. Differentiating between
     * these two cannot be reliably done. Therefore the last part for both
     * of these would be returned: <code>".gz"</code> and
     * <code>".zip"</code> respectively.
     *
     * @param file The file to get the extension of.
     * @return The name of the extension including the <code>.</code>
     * character if possible. Otherwise, <code>null</code>.
     */
    public static String getFileExtension(final File file) {
        return getFileExtension(file.getAbsolutePath());
    }

    /**
     * Gets the index of the last separator irregardless of the target
     * operating system. This allows mixing of separators with no issues
     * with processing. Should both separators be used, the largest index
     * will be returned of the two.
     *
     * @param name The file's absolute path.
     * @return The largest index of either the <code>/</code> or
     * <code>\</code> separator.
     */

    private static int lastSeparatorIndex(final String name) {
        final int forwardSeparator = name.lastIndexOf('/');
        final int backwardSeparator = name.lastIndexOf('\\');
        return Math.max(forwardSeparator, backwardSeparator);
    }

    /**
     * Retrieves the file name without the extension and without the
     * parent directory. This may not for files that have extensions with
     * multiple parts. Everything up to, but excluding, the last
     * <code>.</code> deliminator will be treated as the file's name. This
     * is because of ambiguity with the naming of files. For example:
     * <ul>
     * <li> <code>a1.tar.gz</code>
     * <li> <code>a2.bar.zip</code>
     * </ul>
     * <p>
     * Both of these refer to archives. However, the name of the first
     * archive is <code>"a1"</code> whereas the name of the second archive
     * is <code>"a2.bar"</code>. Differentiating between these two cannot
     * be reliably done. Therefore the approximated name for both of these
     * would be returned: <code>"a1.tar"</code> and <code>"a2.bar"</code>
     * respectively.
     *
     * @param name The file's absolute path.
     * @return The file's parsed name. This may include part of the
     * logical extension in the case that extension has multiple tokens.
     */
    public static String getFileName(final String name) {
        if (name.equals("")) {
            return "";
        }
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

    /**
     * Retrieves the file name without the extension and without the
     * parent directory. This may not for files that have extensions with
     * multiple parts. Everything up to, but excluding, the last
     * <code>.</code> deliminator will be treated as the file's name. This
     * is because of ambiguity with the naming of files. For example:
     * <ul>
     * <li> <code>a1.tar.gz</code>
     * <li> <code>a2.bar.zip</code>
     * </ul>
     * <p>
     * Both of these refer to archives. However, the name of the first
     * archive is <code>"a1"</code> whereas the name of the second archive
     * is <code>"a2.bar"</code>. Differentiating between these two cannot
     * be reliably done. Therefore the approximated name for both of these
     * would be returned: <code>"a1.tar"</code> and <code>"a2.bar"</code>
     * respectively.
     *
     * @param file The file to get the name of.
     * @return The file's parsed name. This may include part of the
     * logical extension in the case that extension has multiple tokens.
     */

    public static String getFileName(final File file) {
        return getFileName(file.getAbsolutePath());
    }

    /**
     * Retrieves the parent's path including the last separator. This
     * therefore allows easy finding of files within the same directory
     * with some name and some extension.
     *
     * @param name The file's absolute path.
     * @return The parent file's absolute path.
     */
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

    /**
     * Retrieves the parent's path including the last separator. This
     * therefore allows easy finding of files within the same directory
     * with some name and some extension.
     *
     * @param file The child file.
     * @return The parent file's absolute path.
     */

    public static String getParentName(final File file) {
        return getParentName(file.getAbsolutePath());
    }

    /**
     * Retrieves the file source of a file within the same directory as
     * the specified file source. This will provide at best an
     * approximation. There is no guarantee that the returned file source
     * will exist.
     * <p>
     * Ideally this functionality would exist within the
     * {@link org.obicere.utility.io.FileSource} itself, however there are
     * numerous issues at the moment. Therefore this is a makeshift
     * approach and expected to be deprecated within future revisions.
     *
     * @param fileSource The source of the file and the basis of the file
     *                   search. Must be non-<code>null</code>.
     * @param otherName  The name of the other file, including the
     *                   extension. Must be non-<code>null</code>.
     * @return The approximate file source of the requested file. This
     * will be non-<code>null</code>, yet might not exist.
     */
    public static FileSource getFileInSameDirectory(final FileSource fileSource, final String otherName) {
        if (fileSource == null) {
            throw new NullPointerException("fileSource must be non-null.");
        }
        if (otherName == null) {
            throw new NullPointerException("otherName must be non-null.");
        }
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

    /**
     * Writes the properties into the specified file. The format for the
     * properties is as follows:
     * <p>
     * <code>key=value</code>
     * <p>
     * Where <code>key</code> and <code>value</code> make up an entry
     * within the map.
     * <p>
     * This will overwrite all changes and <b>not</b>
     * merge with the previous property values. The <code>value</code>s
     * can also contain <code>=</code> characters without there being
     * parse or writing issues. This allows the default way of writing
     * maps as a <code>value</code> permissible.
     * <p>
     * The <code>file</code> does not need to exist before writing.
     *
     * @param properties The properties to write the <code>file</code>.
     *                   Must be non-<code>null</code>.
     * @param file       The file to write the <code>properties</code> to.
     *                   Must
     *                   be non-<code>null</code>.
     * @throws java.io.IOException Should there be an issue with writing
     *                             the properties or creating the file.
     */

    public static void writeProperties(final Map<String, String> properties, final File file) throws IOException {
        if (properties == null) {
            throw new NullPointerException("properties must be non-null");
        }
        if (file == null) {
            throw new NullPointerException("file must be non-null");
        }
        if (!file.canWrite()) {
            throw new IOException("cannot write to the specified file: " + file.getAbsolutePath());
        }
        final PrintWriter writer = new PrintWriter(new FileWriter(file));

        for (final Map.Entry<String, String> entry : properties.entrySet()) {
            final String result = entry.getKey() + "=" + entry.getValue();
            writer.println(result);
        }
        writer.flush();
        writer.close();
    }

    /**
     * Reads the properties into a map. The format for the properties is
     * as follows:
     * <p>
     * <code>key=value</code>
     * <p>
     * Where <code>key</code> and <code>value</code> make up an entry
     * within the map. Comments, which have a <code>#</code> character at
     * the start of the line are skipped and do not effect the output.
     * <p>
     * Should multiple lines define the same <code>key</code>, the line
     * furthest down the file will be the one accepted.
     * <p>
     * If the format is not followed and an invalid line is present, the
     * line will be skipped and then logged with level
     * <code>warning</code>.
     *
     * @param file The file to read the properties from.
     * @return The set of properties read from this <code>file</code>.
     * This will always be non-<code>null</code>.
     * @throws java.io.IOException Should there be any issue with reading
     *                             the properties or opening the file.
     */

    public static Map<String, String> readProperties(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file must be non-null");
        }
        if (!file.canRead()) {
            throw new IOException("cannot read from the specified file: " + file.getAbsolutePath());
        }
        final Map<String, String> properties = new LinkedHashMap<>();
        final BufferedReader reader = new BufferedReader(new FileReader(file));

        String next;
        while ((next = reader.readLine()) != null) {

            // check to see if the line is a comment
            if (next.startsWith("#")) {
                continue;
            }

            // get the first index of the '=' character instead of using
            // String#split to allows '=' characters to be present in
            // values. Then ensure that both key and value are present.
            final int equalIndex = next.indexOf('=');

            // If there is no '=' character or key == "" or value == ""
            if (equalIndex <= 0 || equalIndex + 1 == next.length()) {
                // format does not equal
                Logger.getGlobal().log(Level.WARNING, "Read invalid property line: \"" + next + "\"");
                continue;
            }

            final String key = next.substring(0, equalIndex);
            final String value = next.substring(equalIndex + 1);

            properties.put(key, value);
        }

        reader.close();
        return properties;
    }
}
