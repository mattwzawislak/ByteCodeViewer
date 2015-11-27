package org.obicere.bcviewer.util;

import java.io.IOException;

/**
 * Responsible for reading some object from a data stream. A reader often
 * corresponds to a single format revolving around primitive types. Often,
 * these primitive types are unsigned <code>byte</code>,
 * <code>halfword</code>, <code>word</code> and <code>doubleword</code>
 * values. These values will be read into a data type, corresponding to
 * the type <code>T</code>. In the case the format is failed to be read,
 * instead of returning <code>null</code>, an {@link java.io.IOException}
 * should be thrown describing the error.
 * <h1>An example format:</h1>
 * For example, one may represent a call to a mathematical library as:
 * <pre>{@code call {
 *     u1   function
 *     u2   num_parameters
 *     {
 *          u2  name
 *          u4  value
 *     } parameter[num_parameters]
 * }
 * }</pre>
 * Here, two readers would be recommended. One to read the
 * <code>call</code> format and one to read the <code>parameter</code>
 * format. The types <code>u1</code>, <code>u2</code>, and <code>u4</code>
 * represent an unsigned <code>byte</code>, <code>halfword</code> and
 * <code>word</code>, respectively.
 * <h1>Limitations of the JVM:</h1>
 * The processing of these data types would correlate to the functions
 * available in the
 * {@link org.obicere.bcviewer.util.IndexedDataInputStream} stream. Due to
 * some restrictions on the JVM, <code>u4</code> and <code>u8</code>
 * values may not be represented appropriately. This is because the
 * unsigned <code>doubleword</code> is too large to store while losing any
 * precision and the <code>word</code> - when relating to the index of an
 * array - is beyond the bounds of the JVM.
 * <p>
 * <h1>Relation to a class file format:</h1>
 * The format of the mathematical call format uses the same format
 * specified by the jvms-4.1. Therefore a single one-to-one correlation
 * can be formed between the specification, the available readers and the
 * corresponding data types.
 * <p>
 * For example, the format for a
 * {@link org.obicere.bcviewer.bytecode.ClassFile} as specified is:
 * <pre>
 * {@code ClassFile {
 *      u4             magic;
 *      u2             minor_version;
 *      u2             major_version;
 *      u2             constant_pool_count;
 *      cp_info        constant_pool[constant_pool_count-1];
 *      u2             access_flags;
 *      u2             this_class;
 *      u2             super_class;
 *      u2             interfaces_count;
 *      u2             interfaces[interfaces_count];
 *      u2             fields_count;
 *      field_info     fields[fields_count];
 *      u2             methods_count;
 *      method_info    methods[methods_count];
 *      u2             attributes_count;
 *      attribute_info attributes[attributes_count];
 * }
 * }</pre>
 * This is then mirrored in the corresponding reader:
 * {@link org.obicere.bcviewer.reader.ClassFileReader} which is the
 * responsible reader for this format.
 *
 * Due to the availability of this specification, each reader should
 * explicitly specify the format for which it is reading. And each type
 * should describe for usage of the format in context of the type itself.
 * Any discrepancies between the reader and the format should be noted
 * and explain thoroughly.
 *
 * @author Obicere
 * @version 0.0
 * @see org.obicere.bcviewer.util.MultiReader
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1">jvms-4.1</a>
 * @since 0.0
 */
public interface Reader<T> {

    /**
     * Responsible for reading the stream of primitives into the
     * corresponding data usable by the data type <code>T</code>. The
     * primitive types are unsigned <code>byte</code>,
     * <code>halfword</code>, <code>word</code> and
     * <code>doubleword</code> values. All other formats, including
     * <code>Utf8</code> strings, can be constructed using these types.
     * However, the <code>Utf8</code> type is built in as an available
     * data type. The corresponding functions are:
     * <ul>
     * <li> <code>u1</code> -
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream#readUnsignedByte()}
     * <li> <code>u2</code> -
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream#readUnsignedShort()}
     * <li> <code>u4</code> -
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream#readInt()}
     * <li> <code>u8</code> -
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream#readLong()}
     * <li> <code>Utf8</code> -
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream#readUTF()}
     * </ul>
     * <h1>Limitations of the JVM:</h1>
     * The processing of these data types would correlate to the functions
     * available in the
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream} stream.
     * Due to some restrictions on the JVM, <code>u4</code> and
     * <code>u8</code> values may not be represented appropriately. This
     * is because the unsigned <code>doubleword</code> is too large to
     * store while losing any precision and the <code>word</code> - when
     * relating to the index of an array - is beyond the bounds of the
     * JVM.
     * <h1>Alternative operations:</h1>
     * There are two alternative operations defined by the
     * {@link org.obicere.bcviewer.util.IndexedDataInputStream} that can
     * be used. These involve <code>step</code> and logical indexing
     * functions. The <code>step</code> function allows stepping forwards
     * and backwards through the stream. This is in case formats overlap
     * and the data between formats cannot be easily transmitted. The
     * logical indexing allows readers to operation on a sub-list of
     * <code>bytes</code> without losing the overall index in the actual
     * class file.
     * <p>
     * It may also be necessary to perform alignment operations. This
     * should be done using the stream index as seen fit.
     *
     * @param input The data stream to read the data to fit the known
     *              format into. This format will then correspond to the
     *              type <code>T</code> returned.
     * @return The returned data type. This should never be
     * <code>null</code>. In the event of an error during reading or
     * failure to properly read, an {@link java.io.IOException} should be
     * thrown instead of returning <code>null</code>.
     * @throws java.io.IOException      If there is an error while reading
     *                                  or processing the data. This can
     *                                  also happen if an illegal state is
     *                                  met.
     * @throws java.lang.AssertionError May be thrown in place of an
     *                                  {@link java.io.IOException} should
     *                                  an illegal state be met.
     */
    public T read(final IndexedDataInputStream input) throws IOException;

}
