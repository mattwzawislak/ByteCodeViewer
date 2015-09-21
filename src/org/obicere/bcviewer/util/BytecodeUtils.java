package org.obicere.bcviewer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Obicere
 */
public class BytecodeUtils {

    private static final int ACCESS_PUBLIC       = 0x0001;
    private static final int ACCESS_PRIVATE      = 0x0002;
    private static final int ACCESS_PROTECTED    = 0x0004;
    private static final int ACCESS_STATIC       = 0x0008;
    private static final int ACCESS_FINAL        = 0x0010;
    private static final int ACCESS_SUPER        = 0x0020;
    private static final int ACCESS_SYNCHRONIZED = 0x0020;
    private static final int ACCESS_BRIDGE       = 0x0040;
    private static final int ACCESS_VOLATILE     = 0x0040;
    private static final int ACCESS_TRANSIENT    = 0x0080;
    private static final int ACCESS_VARARGS      = 0x0080;
    private static final int ACCESS_NATIVE       = 0x0100;
    private static final int ACCESS_INTERFACE    = 0x0200;
    private static final int ACCESS_ABSTRACT     = 0x0400;
    private static final int ACCESS_STRICT       = 0x0800;
    private static final int ACCESS_SYNTHETIC    = 0x1000;
    private static final int ACCESS_ANNOTATION   = 0x2000;
    private static final int ACCESS_ENUM         = 0x4000;
    private static final int ACCESS_MANDATED     = 0x8000;

    private static final String NAME_PUBLIC       = "public";
    private static final String NAME_PRIVATE      = "private";
    private static final String NAME_PROTECTED    = "protected";
    private static final String NAME_STATIC       = "static";
    private static final String NAME_FINAL        = "final";
    //private static final String NAME_SUPER        = "";
    private static final String NAME_SYNCHRONIZED = "synchronized";
    //private static final String NAME_BRIDGE       = "";
    private static final String NAME_VOLATILE     = "volatile";
    private static final String NAME_TRANSIENT    = "transient";
    //private static final String NAME_VARARGS      = "";
    private static final String NAME_NATIVE       = "native";
    private static final String NAME_INTERFACE    = "interface";
    private static final String NAME_ABSTRACT     = "abstract";
    private static final String NAME_STRICT       = "strictfp";
    //private static final String NAME_SYNTHETIC    = "";
    private static final String NAME_ANNOTATION   = "@interface";
    private static final String NAME_ENUM         = "enum";
    //private static final String NAME_MANDATED     = "";

    private static final String NAME_CLASS = "class";

    private static final int[] CLASS_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_ABSTRACT,
            ACCESS_STATIC,
            ACCESS_FINAL,
            // ACCESS_STRICT - not possible, as it is compiled out
    };

    private static final String[] CLASS_ORDERED_ACCESS_NAMES = new String[]{
            NAME_PUBLIC,
            NAME_PROTECTED,
            NAME_PRIVATE,
            NAME_ABSTRACT,
            NAME_STATIC,
            NAME_FINAL,
            // ACCESS_STRICT - not possible, as it is compiled out
    };

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

    private static final int[] FIELD_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_STATIC,
            ACCESS_FINAL,
            ACCESS_TRANSIENT,
            ACCESS_VOLATILE
    };

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
     * Retrieves the class access names based off of the given access
     * flags. This can be used to generated a valid 'decompiled' class
     * declaration. The ordering of the flags is set by the jls-8.1.1
     * standard.
     * <p>
     * The last value will be the type of the class. This will default to a
     * <code>class</code> value, in the case that none of the
     * <code>annotation</code>, <code>interface</code> or <code>enum</code>
     * flags are set. Otherwise, the respective type will be applied. In
     * the case that the type is of type <code>annotation</code> or
     * <code>interface</code>, the <code>abstract</code> flag will be
     * removed. In the case that the type is of type <code>enum</code>, the
     * <code>final</code> flag will be removed. This may be due to change,
     * based off of user-based settings.
     * <p>
     * The possible flags for this class, in order of the specification
     * are:
     * <pre>
     * <ul>
     *     <li> public
     *     <li> protected
     *     <li> private
     *     <li> abstract
     *     <li> static
     *     <li> final
     *     <li> annotation
     *     <li> interface
     *     <li> enum
     *     <li> class
     * </ul>
     * </pre>
     * <p>
     * There is a known issue with inner classes, specifically with the
     * <code>protected</code>/<code>private</code>/<code>static</code>
     * access flags. The jvms-4.1 standard specifies that classes must not
     * have these flags set. This is an issue, seeing as inner classes can
     * have these flags set. Therefore, <code>private</code> will be
     * displayed as <code>package-access</code> visibility and
     * <code>protected</code> as <code>public</code> visibility.
     * <code>static</code> will not be included. The true nature of the
     * flags is held in {@link org.obicere.bcviewer.bytecode.InnerClassesAttribute},
     * which is currently inaccessible at this time.
     *
     * @param access The access flags for the class.
     * @return The access flags in order specified by the JLS standard, in
     * an array for easy manipulation.
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

    public static String getQualifiedName(final String className) {
        return className.replace('/', '.');
    }

    public static boolean isPublic(final int access) {
        return (access & ACCESS_PUBLIC) != 0;
    }

    public static boolean isProtected(final int access) {
        return (access & ACCESS_PROTECTED) != 0;
    }

    public static boolean isStatic(final int access) {
        return (access & ACCESS_STATIC) != 0;
    }

    public static boolean isFinal(final int access) {
        return (access & ACCESS_FINAL) != 0;
    }

    public static boolean isSuper(final int access) {
        return (access & ACCESS_SUPER) != 0;
    }

    public static boolean isSynchronized(final int access) {
        return (access & ACCESS_SYNCHRONIZED) != 0;
    }

    public static boolean isBridge(final int access) {
        return (access & ACCESS_BRIDGE) != 0;
    }

    public static boolean isVolatile(final int access) {
        return (access & ACCESS_VOLATILE) != 0;
    }

    public static boolean isTransient(final int access) {
        return (access & ACCESS_TRANSIENT) != 0;
    }

    /**
     * Indicates whether or not the given method takes a variable amount of
     * arguments at the source code level. This should be applied to the
     * last type available, if such a type is present.
     *
     * @param access The access flags for the method.
     * @return <code>true</code> if and only if the method takes a variable
     * number of arguments at the source code level.
     */

    public static boolean isVarargs(final int access) {
        return (access & ACCESS_VARARGS) != 0;
    }

    public static boolean isNative(final int access) {
        return (access & ACCESS_NATIVE) != 0;
    }

    public static boolean isInterface(final int access) {
        return (access & ACCESS_INTERFACE) != 0;
    }

    public static boolean isAbstract(final int access) {
        return (access & ACCESS_ABSTRACT) != 0;
    }

    public static boolean isStrict(final int access) {
        return (access & ACCESS_STRICT) != 0;
    }

    public static boolean isSynthetic(final int access) {
        return (access & ACCESS_SYNTHETIC) != 0;
    }

    public static boolean isAnnotation(final int access) {
        return (access & ACCESS_ANNOTATION) != 0;
    }

    public static boolean isEnum(final int access) {
        return (access & ACCESS_ENUM) != 0;
    }

    public static boolean isMandated(final int access) {
        return (access & ACCESS_MANDATED) != 0;
    }
}
