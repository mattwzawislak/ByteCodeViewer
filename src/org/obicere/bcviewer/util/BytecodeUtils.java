package org.obicere.bcviewer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for representing bytecode information. This includes access
 * flag utilities which allow checking of the existence of certain access
 * flags in the declaration of a class, method or field. This also
 * includes
 * the generation of access flag names.
 * <p>
 * Another utility is the retrieval of class names. This includes
 * replacing
 * all <code>/</code> characters with the <code>.</code> characters. As
 * well as removing the class signature start and end characters:
 * <code>L</code> and <code>;</code> respectively. The full system is
 * described at
 * {@link org.obicere.bcviewer.util.BytecodeUtils#getQualifiedName(String)}
 * as well as
 * {@link org.obicere.bcviewer.util.BytecodeUtils#getClassName(String)},
 * including usages and pitfalls. There is also utility to retrieve
 * package names. This of course just being an approximation at best.
 * <p>
 * These tools should be useful in providing Java source-like code. So
 * instead of dealing with harder-to-read signatures, this can be used to
 * simplify the code down to a more readable level.
 *
 * @author Obicere
 * @version 0.0
 * @since 0.0
 */
public class BytecodeUtils {

    /**
     * The access flag mask for the <code>public</code> keyword. Only one
     * of the visibility access flags should be present in a valid access
     * flags value.
     * <p>
     * The list of other visibility access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * </ul>
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isPublic(int)
     */
    private static final int ACCESS_PUBLIC = 0x0001;


    /**
     * The access flag mask for the <code>private</code> keyword. Only one
     * of the visibility access flags should be present in a valid access
     * flags value.
     * <p>
     * The list of other visibility access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * </ul>
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isPrivate(int)
     */
    private static final int ACCESS_PRIVATE = 0x0002;


    /**
     * The access flag mask for the <code>protected</code> keyword. Only
     * one of the visibility access flags should be present in a valid
     * access flags value.
     * <p>
     * The list of other visibility access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * </ul>
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isProtected(int)
     */
    private static final int ACCESS_PROTECTED = 0x0004;

    /**
     * The access flag mask for the <code>static</code> keyword. This may
     * be removed from redundant cases, such as inner <code>enum</code>
     * <code>class</code> declarations and inner <code>interface</code>
     * declarations.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isStatic(int)
     */
    private static final int ACCESS_STATIC = 0x0008;

    /**
     * The access flag mask for the <code>final</code> keyword. This may
     * be removed from redundant cases, such as in <code>enum</code>
     * <code>class</code> declarations.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isFinal(int)
     */
    private static final int ACCESS_FINAL = 0x0010;

    /**
     * The access flag mask existing for backwards capabilities with the
     * code compiled by older Sun compilers. This should still be present
     * with newer compilers. Although part of a <code>class</code>
     * declaration, this will not be present in the access flag names and
     * therefore should have no <code>String</code> representation.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isSuper(int)
     */
    private static final int ACCESS_SUPER = 0x0020;

    /**
     * The access flag mask for the <code>synchronized</code> keyword.
     * This will only be present in method access flags. Otherwise it is
     * ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isSynchronized(int)
     */
    private static final int ACCESS_SYNCHRONIZED = 0x0020;

    /**
     * The access flag mask used to mark bridge methods. This will not be
     * present in the access flag names and therefore should have no
     * <code>String</code> representation.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isBridge(int)
     */
    private static final int ACCESS_BRIDGE = 0x0040;

    /**
     * The access flag mask for the <code>volatile</code> keyword. This
     * will only be present in field access flags. Otherwise it is
     * ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isVolatile(int)
     */
    private static final int ACCESS_VOLATILE = 0x0040;

    /**
     * The access flag mask for the <code>transient</code> keyword. This
     * will only be present in field access flags. Otherwise it is
     * ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isTransient(int)
     */
    private static final int ACCESS_TRANSIENT = 0x0080;

    /**
     * The access flag mask to indicate the last argument in a method
     * declaration uses variable arguments. This will only be present in
     * the method access flags. Otherwise it is ignored. This will not be
     * present in the access flag names and therefore should have no
     * <code>String</code> representation.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isVarargs(int)
     */
    private static final int ACCESS_VARARGS = 0x0080;

    /**
     * The access flag mask for the <code>native</code> keyword. This will
     * only be present in the method access flags. Otherwise it is
     * ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isNative(int)
     */
    private static final int ACCESS_NATIVE = 0x0100;

    /**
     * The access flag mask for the <code>interface</code> keyword. This
     * keyword is usually also present with the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT}
     * keyword. This will only be present in the class access flags.
     * Otherwise it is ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isInterface(int)
     */
    private static final int ACCESS_INTERFACE = 0x0200;

    /**
     * The access flag mask for the <code>abstract</code> keyword. This
     * keyword is usually also present with the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * and
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * flags for classes. In both of these cases, this flag will be
     * ignored as both of those types infer <code>abstract</code>. This
     * will only be present in method and class access flags.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isAbstract(int)
     */
    private static final int ACCESS_ABSTRACT = 0x0400;

    /**
     * The access flag mask for the <code>strictfp</code> keyword.
     * Although it can be present on a class declaration, it will be
     * compiled out and instead all methods will be attributed with this
     * access flag. Therefore, this will only be present on method access
     * flags. Otherwise it is ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isStrict(int)
     */
    private static final int ACCESS_STRICT = 0x0800;

    /**
     * The access flag mask to signify the class, field or method is
     * synthetic. This may used in conjuncture or in place of the
     * {@link org.obicere.bcviewer.bytecode.SyntheticAttribute}. This will
     * not be present in the access flag names and therefore should have
     * no <code>String</code> representation.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isSynthetic(int)
     */
    private static final int ACCESS_SYNTHETIC = 0x1000;

    /**
     * The access flag mask for the <code>@interface</code> keyword. This
     * keyword is usually also present with the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT}
     * keyword. This will only be present in class access flags. Otherwise
     * it is ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isAnnotation(int)
     */
    private static final int ACCESS_ANNOTATION = 0x2000;

    /**
     * The access flag mask for the <code>enum</code> keyword. This
     * keyword is usually also present with the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL}
     * keyword. This will only be present in class access flags. Otherwise
     * it is ignored.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isEnum(int)
     */
    private static final int ACCESS_ENUM = 0x4000;

    /**
     * The access flag mask to indicate the given parameter is a formal
     * parameter explicitly declared in the source code. This will only
     * be present in a
     * {@link org.obicere.bcviewer.bytecode.MethodParametersAttribute}.
     * More specifically, in the
     * {@link org.obicere.bcviewer.bytecode.Parameter} structure. This
     * will not be present in the access flag names and therefore should
     * have no <code>String</code> representation.
     *
     * @see org.obicere.bcviewer.util.BytecodeUtils#isMandated(int)
     */
    private static final int ACCESS_MANDATED = 0x8000;

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC} flag.
     * This will only be present in class, method and field declarations.
     * Only one of the visibility names should be present in a valid
     * access flags value.
     * <p>
     * The list of other visibility names are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PRIVATE}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PROTECTED}
     * </ul>
     */
    private static final String NAME_PUBLIC = "public";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * flag. This will only be present in class, method and field
     * declarations. Only one of the visibility names should be present in
     * a valid access flags value.
     * <p>
     * The list of other visibility names are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PUBLIC}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PROTECTED}
     * </ul>
     */
    private static final String NAME_PRIVATE = "private";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * flag. This will only be present in class, method and field
     * declarations. Only one of the visibility names should be present in
     * a valid access flags value.
     * <p>
     * The list of other visibility names are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PUBLIC}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PRIVATE}
     * </ul>
     */
    private static final String NAME_PROTECTED = "protected";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STATIC} flag.
     */
    private static final String NAME_STATIC = "static";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL} flag.
     */
    private static final String NAME_FINAL = "final";

    //private static final String NAME_SUPER        = "";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_SYNCHRONIZED}
     * flag.
     */
    private static final String NAME_SYNCHRONIZED = "synchronized";

    //private static final String NAME_BRIDGE       = "";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_VOLATILE}
     * flag.
     */
    private static final String NAME_VOLATILE = "volatile";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_TRANSIENT}
     * flag.
     */
    private static final String NAME_TRANSIENT = "transient";

    //private static final String NAME_VARARGS      = "";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_NATIVE} flag.
     */
    private static final String NAME_NATIVE = "native";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * flag. Only one of the class type access flags will be present. The
     * list of other class access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * </ul>
     */
    private static final String NAME_INTERFACE = "interface";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT}
     * flag.
     */
    private static final String NAME_ABSTRACT = "abstract";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STRICT} flag.
     */
    private static final String NAME_STRICT = "strictfp";

    //private static final String NAME_SYNTHETIC    = "";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * flag. Only one of the class type access flags will be present. The
     * list of other class access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * </ul>
     */
    private static final String NAME_ANNOTATION = "@interface";

    /**
     * The <code>String</code> representation of the
     * {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ENUM} flag.
     * Only one of the class type access flags will be present. The list
     * of other class access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * </ul>
     */
    private static final String NAME_ENUM = "enum";

    //private static final String NAME_MANDATED     = "";

    /**
     * The <code>String</code> representation for the default class type
     * access flags when no other representations are available. The list
     * of other class access flags are:
     * <ul>
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ENUM}
     * <li>{@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE}
     * </ul>
     */
    private static final String NAME_CLASS = "class";

    /**
     * The list of applicable access flags for a class access flag. These
     * are ordered in accordance to the jls-8.1.1 specification. The
     * ordering of these flags are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.1.1">jls-8.1.1</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getClassAccessNames(int)
     */

    private static final int[] CLASS_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_ABSTRACT,
            ACCESS_STATIC,
            ACCESS_FINAL,
            // ACCESS_STRICT - not possible, as it is compiled out
    };

    /**
     * The list of applicable names for a class access flag. These are
     * ordered in accordance to the jls-8.1.1 specification. The ordering
     * of these names are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_ABSTRACT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_FINAL}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.1.1">jls-8.1.1</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getClassAccessNames(int)
     */

    private static final String[] CLASS_ORDERED_ACCESS_NAMES = new String[]{
            NAME_PUBLIC,
            NAME_PROTECTED,
            NAME_PRIVATE,
            NAME_ABSTRACT,
            NAME_STATIC,
            NAME_FINAL,
            // ACCESS_STRICT - not possible, as it is compiled out
    };

    /**
     * The list of applicable access flags for a method access flag. These
     * are ordered in accordance to the jls-8.4.3 specification. The
     * ordering of these flags are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_SYNCHRONIZED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_NATIVE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STRICT}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.3">jls-8.4.3</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getMethodAccessNames(int)
     */

    private static final int[] METHOD_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_ABSTRACT,
            ACCESS_STATIC,
            ACCESS_FINAL,
            ACCESS_SYNCHRONIZED,
            ACCESS_NATIVE,
            ACCESS_STRICT
    };

    /**
     * The list of applicable names for a method access flag. These are
     * ordered in accordance to the jls-8.4.3 specification. The ordering
     * of these names are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_ABSTRACT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_FINAL}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_SYNCHRONIZED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_NATIVE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_STRICT}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.3">jls-8.4.3</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getMethodAccessNames(int)
     */

    private static final String[] METHOD_ORDERED_ACCESS_NAMES = new String[]{
            NAME_PUBLIC,
            NAME_PROTECTED,
            NAME_PRIVATE,
            NAME_ABSTRACT,
            NAME_STATIC,
            NAME_FINAL,
            NAME_SYNCHRONIZED,
            NAME_NATIVE,
            NAME_STRICT
    };

    /**
     * The list of applicable access flags for a field access flag. These
     * are ordered in accordance to the jls-8.3.1 specification. The
     * ordering of these flags are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_TRANSIENT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#ACCESS_VOLATILE}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1">jls-8.3.1</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getFieldAccessNames(int)
     */

    private static final int[] FIELD_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_STATIC,
            ACCESS_FINAL,
            ACCESS_TRANSIENT,
            ACCESS_VOLATILE
    };

    /**
     * The list of applicable names for a field access flag. These are
     * ordered in accordance to the jls-8.3.1 specification. The ordering
     * of these names are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PUBLIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PROTECTED}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_PRIVATE}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_STATIC}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_FINAL}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_TRANSIENT}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#NAME_VOLATILE}
     * </ul>
     *
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1">jls-8.3.1</a>
     * @see org.obicere.bcviewer.util.BytecodeUtils#getFieldAccessNames(int)
     */

    private static final String[] FIELD_ORDERED_ACCESS_NAMES = new String[]{
            NAME_PUBLIC,
            NAME_PROTECTED,
            NAME_PRIVATE,
            NAME_STATIC,
            NAME_FINAL,
            NAME_TRANSIENT,
            NAME_VOLATILE
    };

    /**
     * Illegal operation. Cannot be instantiated.
     */
    private BytecodeUtils() {
        throw new AssertionError();
    }

    /**
     * Retrieves the class access names based off of the given access
     * flags. This can be used to generated a valid 'decompiled' class
     * declaration. The ordering of the flags is set by the jls-8.1.1
     * standard.
     * <p>
     * The last value will be the type of the class. This will default to
     * a <code>class</code> value, in the case that none of the
     * <code>annotation</code>, <code>interface</code> or
     * <code>enum</code>
     * flags are set. Otherwise, the respective type will be applied. In
     * the case that the type is of type <code>annotation</code> or
     * <code>interface</code>, the <code>abstract</code> flag will be
     * removed. In the case that the type is of type <code>enum</code>,
     * the <code>final</code> flag will be removed. This may be due to
     * change, based off of user-based settings.
     * <p>
     * The possible flags for this class, in order of the specification
     * are:
     * <ul>
     * <li> public
     * <li> protected
     * <li> private
     * <li> abstract
     * <li> static
     * <li> final
     * <li> annotation
     * <li> interface
     * <li> enum
     * <li> class
     * </ul>
     * <p>
     * There is a known issue with inner classes, specifically with the
     * <code>protected</code>/<code>private</code>/<code>static</code>
     * access flags. The jvms-4.1 standard specifies that classes must not
     * have these flags set. This is an issue, seeing as inner classes can
     * have these flags set. Therefore, <code>private</code> will be
     * displayed as <code>package-access</code> visibility and
     * <code>protected</code> as <code>public</code> visibility.
     * <code>static</code> will not be included. The true nature of the
     * flags is held in
     * {@link org.obicere.bcviewer.bytecode.InnerClassesAttribute}, which
     * is currently inaccessible at this time.
     *
     * @param access The access flags for the class.
     * @return The access flags in order specified by the jls standard, in
     * an array for easy manipulation.
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.1.1">jls-8.1.1</a>
     */

    public static String[] getClassAccessNames(final int access) {
        final int length = CLASS_ORDERED_ACCESS_FLAGS.length; // same length as names
        final List<String> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            if ((CLASS_ORDERED_ACCESS_FLAGS[i] & access) != 0) { // if the flag is set
                list.add(CLASS_ORDERED_ACCESS_NAMES[i]);
            }
        }

        // handle the type of the class here
        if (isAnnotation(access)) {
            list.add(NAME_ANNOTATION);
            list.remove(NAME_ABSTRACT); // abstract not necessary since interface
            list.remove(NAME_STATIC); // static not necessary since interface
        } else if (isInterface(access)) {
            list.add(NAME_INTERFACE);
            list.remove(NAME_ABSTRACT); // abstract not necessary since interface
            list.remove(NAME_STATIC); // static not necessary since interface
        } else if (isEnum(access)) {
            list.add(NAME_ENUM);
            list.remove(NAME_FINAL); // final not necessary since enum
            list.remove(NAME_STATIC); // static not necessary since enum
        } else { // otherwise, must be a class
            list.add(NAME_CLASS);
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Retrieves the method access named based off of the given access
     * flags. This can be used to generated a valid 'decompiled' method
     * declaration. The ordering of the flags is set by the jls-8.4.3
     * standard.
     * <p>
     * There is a case where the access flags may be seen as 'incorrect'
     * is
     * if the containing class is declared as an <code>interface</code>.
     * In which case, the method flags will redundantly contain the
     * <code>abstract</code> tag.
     * <p>
     * The possible flags for this method, in order of this specification
     * are:
     * <ul>
     * <li> public
     * <li> protected
     * <li> private
     * <li> abstract
     * <li> static
     * <li> final
     * <li> synchronized
     * <li> native
     * <li> strictfp
     * </ul>
     * Only one of the <code>public</code>, <code>protected</code>,
     * <code>private</code> will be listed, if any. In the case the field
     * has <code>package-access</code>, none of visibility modifiers will
     * be returned.
     *
     * @param access The access flags for the method.
     * @return The access flags in order specified by the jls standard, in
     * an array for easy manipulation.
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.3">jls-8.4.3</a>
     */

    public static String[] getMethodAccessNames(final int access) {
        final int length = METHOD_ORDERED_ACCESS_FLAGS.length; // same length as names
        final List<String> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            if ((METHOD_ORDERED_ACCESS_FLAGS[i] & access) != 0) { // if the flag is set
                list.add(METHOD_ORDERED_ACCESS_NAMES[i]);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Retrieves the field access names based off of the given access
     * flags. This can be used to generate a valid 'decompiled' field
     * declaration. The ordering of the flags is set by the jls-8.3.1
     * standard.
     * <p>
     * The possible flags for this field, in order of the specification
     * are:
     * <ul>
     * <li> public
     * <li> protected
     * <li> private
     * <li> static
     * <li> final
     * <li> transient
     * <li> volatile
     * </ul>
     * <p>
     * Only one of the <code>public</code>, <code>protected</code>,
     * <code>private</code> will be listed, if any. In the case the field
     * has <code>package-access</code>, none of visibility modifiers will
     * be returned.
     *
     * @param access The access flags for the field.
     * @return The access flags in order specified by the jls standard, in
     * an array for easy manipulation.
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1">jls-8.3.1</a>
     */
    public static String[] getFieldAccessNames(final int access) {
        final int length = FIELD_ORDERED_ACCESS_FLAGS.length; // same length as names
        final List<String> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            if ((FIELD_ORDERED_ACCESS_FLAGS[i] & access) != 0) { // if the flag is set
                list.add(FIELD_ORDERED_ACCESS_NAMES[i]);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns the fully qualified class name. This will replace all
     * <code>/</code> deliminators with <code>.</code> deliminators. It
     * will remove the starting <code>L</code> and <code>;</code>
     * characters if present.
     * <p>
     * For example, the qualified name <code>com.example.Foo</code> would
     * be the result of calling this method on all of the following cases:
     * <ul>
     * <li><code>com.example.Foo</code>
     * <li><code>com/example/Foo</code>
     * <li><code>Lcom.example.Foo;</code>
     * <li><code>Lcom/example/Foo;</code>
     * </ul>
     * <p>
     * There may be some ambiguity from this. Consider a class in the
     * package <code>com.example</code> named <code>Foo</code> with an
     * inner class <code>Bar</code>. With this example, the class entry
     * will be named <code>"Lcom/example/Foo.Bar;"</code>, and upon
     * calling
     * this method the result will be <code>"com.example.Foo.Bar"</code>.
     * This provides ambiguity, as <code>Foo</code> could be either a
     * class or a package. This makes the actual class declaration and
     * package declaration ambiguous.
     * <p>
     * Due to this ambiguity, it is preferred not to use this method if
     * the declarations need to be kept separate, but instead calling the
     * appropriate functions to parse the names.
     *
     * @param className The name of the class to parse as a qualified
     *                  class name.
     * @return The qualified class name with all <code>/</code> characters
     * replaced with <code>.</code> characters. And the leading
     * <code>L</code> and trailing <code>;</code> characters removed if
     * necessary.
     * @throws java.lang.NullPointerException if <code>className</code> is
     *                                        <code>null</code>.
     */
    public static String getQualifiedName(String className) {
        if (className == null) {
            throw new NullPointerException("className must be non-null.");
        }
        final String clean = removeDeclarationCharacters(className);
        return clean.replace('/', '.');
    }

    /**
     * Attempts to retrieve the full class name which includes outer
     * classes. So for example, with the <code>"java/util/Map.Entry"</code>
     * class, this would return <code>"Map.Entry"</code> as the full class
     * name.
     * <p>
     * However, there is some ambiguity. If the forward slashes have been
     * replaced with <code>.</code> deliminators, then the full class name
     * cannot be parsed properly and the <code>className</code> is
     * returned with only the leading <code>L</code> and trailing
     * <code>;</code> characters removed.
     * <p>
     * This is because if we have the following class:
     * <p>
     * <code>com.example.Foo.Bar</code>
     * <p>
     * The process of determining whether <code>Foo</code> is a package or
     * a class cannot be reliably done. So the full name
     * <code>com.example.Foo.Bar</code> will be returned.
     * <p>
     * This can be avoided altogether by not replacing the <code>/</code>
     * deliminators with <code>.</code>deliminators. As shown with our
     * example, if <code>Foo</code> were to be a class:
     * <p>
     * <code>com/example/Foo.Bar</code>
     * <p>
     * Or if <code>Foo</code> were to be a package:
     * <p>
     * <code>com/example/Foo/Bar</code>
     * <p>
     * The distinction between the two is apparent and the appropriate
     * name will be returned.
     *
     * @param className The name of the class to attempt to parse out a
     *                  full name from.
     * @return The full class name with outer classes listed and with the
     * leading <code>L</code> and trailing <code>;</code> characters
     * removed, if applicable.
     * @throws java.lang.NullPointerException if <code>className</code> is
     *                                        <code>null</code>.
     */
    public static String getFullClassName(final String className) {
        if (className == null) {
            throw new NullPointerException("className must be non-null.");
        }
        final String clean = removeDeclarationCharacters(className);
        final int index = clean.lastIndexOf('/');
        if (index > 0) {
            return clean.substring(index + 1);
        } else {
            // Slashes may have been replaced with '.' characters. This
            // can result in ambiguity for the package and nested classes.
            return clean;
        }
    }

    /**
     * Retrieves the class name excluding the outer classes. So for
     * example, with the <code>"java/util/Map.Entry"</code> class, this
     * would return <code>"Entry"</code> as the class name.
     * <p>
     * This avoids the potential ambiguity of mixing up package and class
     * declarations, as only the last token is returned - which will
     * always be the class name.
     * <p>
     * However, this will not fully operate on anonymous inner classes.
     * This assumes the class names are from signatures and not from a
     * file.
     *
     * @param className The name of the class to parse out a name from.
     * @return The class name without outer classes listed and with the
     * leading <code>L</code> and trailing <code>;</code> characters
     * removed, if applicable.
     * @throws java.lang.NullPointerException if <code>className</code> is
     *                                        <code>null</code>.
     */
    public static String getClassName(final String className) {
        // getQualifiedName will throw NullPointerException if null
        final String qualified = getQualifiedName(className);
        final int index = qualified.lastIndexOf('.');
        // get the last index of the '.', everything past that is the
        // class name
        if (index > 0) {
            return qualified.substring(index + 1);
        } else {
            return className;
        }
    }

    /**
     * Retrieves the package for the given class. So for example, with the
     * <code>"java/util/Map.Entry"</code> class, this would return
     * <code>"java.util"</code> as the package name.
     * <p>
     * However, there is some ambiguity. If the forward slashes have been
     * replaced with <code>.</code> deliminators, then the package name
     * cannot be parsed properly and the empty <code>String</code> is
     * returned.
     * <p>
     * This is because if we have the following class:
     * <p>
     * <code>com.example.Foo.Bar</code>
     * <p>
     * The process of determining whether <code>Foo</code> is a package or
     * a class cannot be reliably done. So the <code>""</code> will be
     * returned.
     * <p>
     * This can be avoided altogether by not replacing the <code>/</code>
     * deliminators with <code>.</code>deliminators. As shown with our
     * example, if <code>Foo</code> were to be a class:
     * <p>
     * <code>com/example/Foo.Bar</code>
     * <p>
     * Or if <code>Foo</code> were to be a package:
     * <p>
     * <code>com/example/Foo/Bar</code>
     * <p>
     * The distinction between the two is apparent and the appropriate
     * package will be returned.
     *
     * @param className The name of the class to attempt to parse out a
     *                  package name from.
     * @return The name of the package, with <code>.</code> deliminators
     * if possible. If there is no package name or any ambiguity, the
     * <code>String</code> <code>""</code> will be returned.
     * @throws java.lang.NullPointerException if <code>className</code> is
     *                                        <code>null</code>.
     */
    public static String getPackage(final String className) {
        if (className == null) {
            throw new NullPointerException("className must be non-null.");
        }
        final String clean = removeDeclarationCharacters(className);
        final int slashIndex = clean.lastIndexOf('/');
        // check to see if the slash is available and we can avoid
        // ambiguity
        if (slashIndex > 0) {
            return clean.substring(0, slashIndex);
        } else {
            return "";
        }
    }

    /**
     * Removes the leading <code>L</code> and trailing <code>;</code>
     * characters removed, if applicable. This is safe in the sense that
     * both characters need to be present for either to be removed. So for
     * example:
     * <ul>
     * <li><code>"Bar"</code>      -&gt; <code>"Bar"</code>
     * <li><code>"Language"</code> -&gt; <code>"Language"</code>
     * <li><code>"Foo;"</code>     -&gt; <code>"Foo;"</code>
     * <li><code>"LTee;"</code>    -&gt; <code>"Tee"</code>
     * </ul>
     *
     * @param className A non-<code>null</code> class name.
     * @return The class name with the leading <code>L</code> and trailing
     * <code>;</code> characters removed, if applicable.
     */
    private static String removeDeclarationCharacters(final String className) {
        if (className.startsWith("L") && className.endsWith(";")) {
            return className.substring(1, className.length() - 1);
        }
        return className;
    }

    /**
     * Indicates whether or not the given class, method or field has
     * <code>public</code> visibility.
     * <p>
     * For a valid access flag, at most one of the visibility modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * <code>package-access</code> visibility.
     * <p>
     * The other visibility checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isPrivate(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isProtected(int)}
     * </ul>
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * <code>public</code> visibility.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PUBLIC
     */

    public static boolean isPublic(final int access) {
        return (access & ACCESS_PUBLIC) != 0;
    }

    /**
     * Indicates whether or not the given class, method or field has
     * <code>private</code> visibility.
     * <p>
     * For a valid access flag, at most one of the visibility modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * <code>package-access</code> visibility.
     * <p>
     * The other visibility checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isPublic(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isProtected(int)}
     * </ul>
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * <code>private</code> visibility.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PRIVATE
     */

    public static boolean isPrivate(final int access) {
        return (access & ACCESS_PRIVATE) != 0;
    }

    /**
     * Indicates whether or not the given class, method or field has
     * <code>protected</code> visibility.
     * <p>
     * For a valid access flag, at most one of the visibility modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * <code>package-access</code> visibility.
     * <p>
     * The other visibility checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isPublic(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isPrivate(int)}
     * </ul>
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * <code>protected</code> visibility.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_PROTECTED
     */

    public static boolean isProtected(final int access) {
        return (access & ACCESS_PROTECTED) != 0;
    }

    /**
     * Indicates whether or not the given class, method or field is
     * <code>static</code>.
     * <p>
     * For inner <code>enum</code>, <code>interface</code> or
     * <code>annotation</code> types, this is inferred and therefore may
     * not appear in the access flag names even if set.
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * <code>static</code> accessibility.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STATIC
     */

    public static boolean isStatic(final int access) {
        return (access & ACCESS_STATIC) != 0;
    }

    /**
     * Indicates whether or not the given class, method or field is
     * <code>final</code>.
     * <p>
     * For <code>enum</code> types, this is inferred and therefore may not
     * appear in the access flag names even if set.
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * the element is <code>final</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_FINAL
     */

    public static boolean isFinal(final int access) {
        return (access & ACCESS_FINAL) != 0;
    }

    /**
     * Indicates the class uses an alternative semantic for super method
     * invocations. This exists for backwards capabilities with the code
     * compiled by older Sun compilers. This should still be present  with
     * newer compilers.
     *
     * @param access The access flags for the class.
     * @return <code>true</code> if and only if the access flags indicate
     * the class uses the alternative semantic.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_SUPER
     */

    public static boolean isSuper(final int access) {
        return (access & ACCESS_SUPER) != 0;
    }

    /**
     * Indicates whether or not the given method is
     * <code>synchronized</code>.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the access flags indicate
     * the method is <code>synchronized</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_SYNCHRONIZED
     */

    public static boolean isSynchronized(final int access) {
        return (access & ACCESS_SYNCHRONIZED) != 0;
    }

    /**
     * Indicates whether or not the given method is a bridge method for
     * generic compilation.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the access flags indicate
     * the method is a bridge.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_BRIDGE
     */
    public static boolean isBridge(final int access) {
        return (access & ACCESS_BRIDGE) != 0;
    }

    /**
     * Indicates whether or not the given field is <code>volatile</code>.
     *
     * @param access The access flags for the field.
     * @return <code>true</code> if and only if the access flags indicate
     * the field is <code>volatile</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_VOLATILE
     */
    public static boolean isVolatile(final int access) {
        return (access & ACCESS_VOLATILE) != 0;
    }

    /**
     * Indicates whether or not the given field is <code>transient</code>.
     *
     * @param access The access flags for the field.
     * @return <code>true</code> if and only if the access flags indicate
     * the field is <code>transient</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_TRANSIENT
     */

    public static boolean isTransient(final int access) {
        return (access & ACCESS_TRANSIENT) != 0;
    }

    /**
     * Indicates whether or not the given method takes a variable amount
     * of
     * arguments at the source code level. This should be applied to the
     * last type available, if such a type is present.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the access flags indicate
     * the method takes variable arguments.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_VARARGS
     */

    public static boolean isVarargs(final int access) {
        return (access & ACCESS_VARARGS) != 0;
    }

    /**
     * Indicates whether or not the given method is <code>native</code>.
     * <p>
     * Methods listed as <code>native</code> should have no body and no
     * {@link org.obicere.bcviewer.bytecode.CodeAttribute} available.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the access flags indicate
     * the method is <code>native</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_NATIVE
     */
    public static boolean isNative(final int access) {
        return (access & ACCESS_NATIVE) != 0;
    }

    /**
     * Indicates whether or not the given class is an
     * <code>interface</code> type.
     * <p>
     * For a valid access flag, at most one of the class type modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * the class type is a <code>class</code>.
     * <p>
     * The other class type checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isAnnotation(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isEnum(int)}
     * </ul>
     *
     * @param access The access flags for the class.
     * @return <code>true</code> if and only if the access flags indicate
     * the class type is an <code>interface</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_INTERFACE
     */

    public static boolean isInterface(final int access) {
        return (access & ACCESS_INTERFACE) != 0;
    }

    /**
     * Indicates whether or not the given class or method is
     * <code>abstract</code>.
     * <p>
     * For <code>interface</code> types and <code>annotation</code> types,
     * this is inferred and therefore may not appear in the access flag
     * names even if set.
     * <p>
     * Although it is also true that this is inferred for the methods of
     * said types, it cannot be ensured and therefore this will appear in
     * the method access flag names for <code>interface</code> types and
     * for <code>annotation</code> types.
     *
     * @param access The access flags for the class or method.
     * @return <code>true</code> if and only if the access flags indicate
     * the class or method is <code>abstract</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ABSTRACT
     */

    public static boolean isAbstract(final int access) {
        return (access & ACCESS_ABSTRACT) != 0;
    }

    /**
     * Indicates whether or not the given method is <code>strictfp</code>.
     * <p>
     * Even though classes can be declared as <code>strictfp</code>, this
     * is compiled out and instead applied to all of the methods.
     * Therefore, this will not appear in the access flag names of a class
     * even if the source code suggests it should.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the access flags indicate
     * the method is <code>strictfp</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_STRICT
     */

    public static boolean isStrict(final int access) {
        return (access & ACCESS_STRICT) != 0;
    }

    /**
     * Indicates whether or not the given class, method or field is
     * synthetically created.
     * <p>
     * This is often used in place of or along side the
     * {@link org.obicere.bcviewer.bytecode.SyntheticAttribute}.
     *
     * @param access The access flags for the class, method or field.
     * @return <code>true</code> if and only if the access flags indicate
     * the class, method or field is synthetic.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_SYNTHETIC
     */

    public static boolean isSynthetic(final int access) {
        return (access & ACCESS_SYNTHETIC) != 0;
    }

    /**
     * Indicates whether or not the given class is an
     * <code>annotation</code> type.
     * <p>
     * For a valid access flag, at most one of the class type modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * the class type is a <code>class</code>.
     * <p>
     * The other class type checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isInterface(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isEnum(int)}
     * </ul>
     *
     * @param access The access flags for the class.
     * @return <code>true</code> if and only if the access flags indicate
     * the class type is an <code>annotation</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ANNOTATION
     */

    public static boolean isAnnotation(final int access) {
        return (access & ACCESS_ANNOTATION) != 0;
    }

    /**
     * Indicates whether or not the given class is an <code>enum</code>
     * type.
     * <p>
     * For a valid access flag, at most one of the class type modifiers
     * must return <code>true</code>. If none do, then the flags indicate
     * the class type is a <code>class</code>.
     * <p>
     * The other class type checks are:
     * <ul>
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isInterface(int)}
     * <li> {@link org.obicere.bcviewer.util.BytecodeUtils#isAnnotation(int)}
     * </ul>
     *
     * @param access The access flags for the class.
     * @return <code>true</code> if and only if the access flags indicate
     * the class type is an <code>enum</code>.
     * @see org.obicere.bcviewer.util.BytecodeUtils#ACCESS_ENUM
     */

    public static boolean isEnum(final int access) {
        return (access & ACCESS_ENUM) != 0;
    }

    /**
     * Indicates whether or not the given method parameter is a formal
     * and therefore mandated parameter.
     * <p>
     * This is used only with the
     * {@link org.obicere.bcviewer.bytecode.MethodParametersAttribute} to
     * signify the existence of an explicitly stated formal parameter in
     * the source code.
     *
     * @param access The access flags for the method parameter.
     * @return <code>true</code> if and only if the method parameter is
     * formal and mandated.
     */

    public static boolean isMandated(final int access) {
        return (access & ACCESS_MANDATED) != 0;
    }
}
