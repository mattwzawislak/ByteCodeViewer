/**
 * Utilities corresponding to the functionality of the application. These
 * include I/O processing and data modification.
 * <p>
 * The {@link org.obicere.bcviewer.util.BytecodeUtils} is responsible for
 * modifying the data of the class file to allow smart changes to be made.
 * This includes, but is not limited to, the parsing of class names,
 * package names, access flags and checking for the existence of an access
 * modifier.
 * <p>
 * The {@link org.obicere.bcviewer.util.FileUtils} is responsible for
 * handling operations involving class files utilizing the
 * {@link org.obicere.utility.io.FileSource} system.
 * <p>
 * The {@link org.obicere.bcviewer.util.FontUtils} refer to the available
 * fonts on the system, just to provide user options when it comes to the
 * text editors.
 * <p>
 * The remaining utilities correspond to the reading and storage of file
 * formats and data. These were made in reference to the jvms. More
 * specifically, the specification regarding the class file format.
 * Therefore, the specification regarding how the data is processed and
 * should be stored is in the jvms-4.
 * <p>
 * The classes for reading the class file format are found in the:
 * {@link org.obicere.bcviewer.reader} <code>package</code>.
 * <p>
 * @author Obicere
 * @version 0.0
 * @since 0.0
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html">jvms-4</a>
 */
package org.obicere.bcviewer.util;