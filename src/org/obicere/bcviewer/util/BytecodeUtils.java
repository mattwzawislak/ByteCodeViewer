package org.obicere.bcviewer.util;

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

    private static final int[] CLASS_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_ABSTRACT,
            ACCESS_STATIC,
            ACCESS_FINAL,
            ACCESS_STRICT
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

    private static final int[] FIELD_ORDERED_ACCESS_FLAGS = new int[]{
            ACCESS_PUBLIC,
            ACCESS_PROTECTED,
            ACCESS_PRIVATE,
            ACCESS_STATIC,
            ACCESS_FINAL,
            ACCESS_TRANSIENT,
            ACCESS_VOLATILE
    };

}
