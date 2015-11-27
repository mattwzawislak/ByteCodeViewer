package org.obicere.bcviewer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for handling multiple readers depending on some identifier.
 * This allows cases where the format defines an abstract format with many
 * concrete formats that could be ran. This is therefore most
 * representative of a union. The purpose of this, as opposed to a single,
 * much larger reader, is that this allows flexibility. A reader often
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
 * {@link org.obicere.bcviewer.bytecode.instruction.Instruction} follows
 * the loose form of:
 * <pre>{@code Instruction {
 *     u1   opcode
 *     u1   operand1
 *     u1   operand2
 *     ...
 * }
 * }</pre>
 * However, this changes from instruction-to-instruction. For example, the
 * {@link org.obicere.bcviewer.bytecode.instruction.nop} format is just:
 * <pre>{@code nop {
 *     u1   opcode
 * }
 * }</pre>
 * Contrasted to the
 * {@link org.obicere.bcviewer.bytecode.instruction.aload} format:
 * <pre>{@code aload {
 *     u1   opcode
 *     u1   indexbyte1
 *     u1   indexbyte2
 * }
 * }</pre>
 * To provide a single reader for both of these cases would be overly
 * complex and prone to error. Therefore three readers would be defined.
 * Two of which read the independent formats and the third, a multi-reader
 * , which redirects the format to the appropriate independent reader.
 * <p>
 * This is accomplished by linking each independent reader to a
 * corresponding key <code>K</code>. The given format should therefore
 * define a key for this to be used. Otherwise, there is no way a
 * multi-reader could be used, as differentiating between formats would be
 * obscure and too costly.
 * <p>
 * This is then mirrored in the corresponding reader:
 * {@link org.obicere.bcviewer.reader.instruction.InstructionReader} which
 * is the responsible reader for the independent readers.
 * <p>
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
public abstract class MultiReader<K, T> implements Reader<T> {

    /**
     * The map containing the readers identifiable by some key
     * <code>K</code>. The given entry is then the corresponding reader
     * that reads the format for that key.
     */
    private final Map<K, Reader<? extends T>> readers = new HashMap<>();

    /**
     * Gets the reader based off of the given <code>key</code>. This
     * allows for <code>null</code> keys, allowing for a general fall-back
     * case.
     * <p>
     * The returned {@link org.obicere.bcviewer.util.Reader} will only be
     * <code>null</code> if there is no reader corresponding to this
     * <code>key</code>. This should be treated as an illegal state as the
     * format therefore defines additional formats not included in the
     * given specification or there was data corruption.
     *
     * @param key The key to identify the individual reader by.
     * @return The individual reader corresponding to the given
     * <code>key</code>. This will only be <code>null</code> if an illegal
     * state has been reached.
     */
    public final Reader<? extends T> get(final K key) {
        return readers.get(key);
    }

    /**
     * Adds a new <code>key</code>-<code>reader</code> pair. The given
     * <code>key</code> will correspond to the individual format used to
     * properly read an some abstract format.
     *
     * @param key    The key to identify the individual
     *               <code>reader</code> by. This can be <code>null</code>
     *               to handle general cases.
     * @param reader The reader that corresponds to the given
     *               <code>key</code>. This must be non-<code>null</code>.
     * @throws java.lang.NullPointerException if the given
     *                                        <code>reader</code> is
     *                                        <code>null</code>.
     */
    public final void add(final K key, final Reader<? extends T> reader) {
        if (reader == null) {
            throw new NullPointerException("reader cannot be null.");
        }
        readers.put(key, reader);
    }

    /**
     * Removes the given reader that corresponds to this <code>key</code>.
     * Removing any individual format can pose the possibility that an
     * illegal state could be reached. Usually, an individual reader would
     * be replaced with an alternative reader. In this case, this method
     * does not need to be called. Instead, just calling
     * {@link org.obicere.bcviewer.util.MultiReader#add(K, Reader)} will
     * replace the reader.
     *
     * @param key The key to identify the individual reader by.
     * @return <code>true</code> if the given reader was removed.
     */
    public final boolean remove(final K key) {
        return readers.remove(key) != null;
    }

    /**
     * Removes the given reader and key that corresponds to this reader.
     * Removing any individual format can pose the possibility that an
     * illegal state could be reached. Usually, an individual reader would
     * be replaced with an alternative reader. In this case, this method
     * does not need to be called. Instead, just calling
     * {@link org.obicere.bcviewer.util.MultiReader#add(K, Reader)} will
     * replace the reader.
     * <p>
     * This will remove all instances of the <code>reader</code>. If only
     * a single individual reader should be removed, it must be removed by
     * its unique <code>key</code>.
     *
     * @param reader The individual reader to be removed.
     * @return <code>true</code> if the given reader was removed.
     */
    public final boolean remove(final Reader<? extends T> reader) {
        boolean removed = false;
        for (final Map.Entry<K, Reader<? extends T>> entry : readers.entrySet()) {
            // we found an entry with this reader
            if (entry.getValue() == reader || entry.getValue().equals(reader)) {
                // remove the entry and continue looking for more
                // occurences
                readers.remove(entry.getKey());
                removed = true;
            }
        }
        return removed;
    }
}
