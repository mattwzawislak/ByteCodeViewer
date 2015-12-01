package org.obicere.bytecode.viewer.gui.swing.tree;

import com.sun.istack.internal.NotNull;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * @author Obicere
 */
public class ByteCodeTreeNode extends DefaultMutableTreeNode implements Comparable<ByteCodeTreeNode> {

    private static final String[] ANNOTATION_ICONS = new String[]{
            Icons.ICON_ANNOTATION_DEFAULT,
            Icons.ICON_ANNOTATION_PUBLIC,
            Icons.ICON_ANNOTATION_PROTECTED,
            Icons.ICON_ANNOTATION_PRIVATE
    };

    private static final String[] CLASS_ICONS = new String[]{
            Icons.ICON_CLASS_DEFAULT,
            Icons.ICON_CLASS_PUBLIC,
            Icons.ICON_CLASS_PROTECTED,
            Icons.ICON_CLASS_PRIVATE
    };

    private static final String[] ENUM_ICONS = new String[]{
            Icons.ICON_ENUM_DEFAULT,
            Icons.ICON_ENUM_PUBLIC,
            Icons.ICON_ENUM_PROTECTED,
            Icons.ICON_ENUM_PRIVATE
    };

    private static final String[] INTERFACE_ICONS = new String[]{
            Icons.ICON_INTERFACE_DEFAULT,
            Icons.ICON_INTERFACE_PUBLIC,
            Icons.ICON_INTERFACE_PROTECTED,
            Icons.ICON_INTERFACE_PRIVATE
    };

    private final Icon icon;
    private final Icon disabledIcon;

    private final boolean isPackage;

    public ByteCodeTreeNode(final Icon icon, final Icon disabledIcon, final Object object) {
        this(icon, disabledIcon, object, false);
    }

    public ByteCodeTreeNode(final Icon icon, final Icon disabledIcon, final Object object, final boolean isPackage) {
        super(object);
        this.icon = icon;
        this.disabledIcon = disabledIcon;
        this.isPackage = isPackage;
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getDisabledIcon() {
        return disabledIcon;
    }

    public boolean isPackage() {
        return isPackage;
    }

    public int getIndexFor(final ByteCodeTreeNode node) {

        final Enumeration children = children();

        int index = 0;
        while (children.hasMoreElements()) {
            final ByteCodeTreeNode child = (ByteCodeTreeNode) children.nextElement();
            if (node.compareTo(child) <= 0) {
                return index;
            }
            index++;
        }
        return index;
    }

    public static ByteCodeTreeNode buildNode(final Domain domain, final ClassFile classFile) {
        return buildNode(domain, classFile, classFile.getAccessFlags());
    }

    public static ByteCodeTreeNode buildNode(final Domain domain, final ClassFile classFile, final int accessFlags) {
        final String[] group = getIconsGroup(accessFlags);
        final int index = getVisibilityIndex(accessFlags);
        final String other = getDisabledIcon(accessFlags);

        final Icons icons = domain.getIcons();
        final Icon icon = icons.getIcon(group[index]);
        final Icon disabledIcon = icons.getIcon(other);

        return new ByteCodeTreeNode(icon, disabledIcon, ByteCodeUtils.getClassName(classFile.getName()));
    }

    private static int getVisibilityIndex(final int accessFlags) {
        if (ByteCodeUtils.isPublic(accessFlags)) {
            return 1;
        } else if (ByteCodeUtils.isProtected(accessFlags)) {
            return 2;
        } else if (ByteCodeUtils.isPrivate(accessFlags)) {
            return 3;
        } else {
            return 0;
        }
    }

    private static String getDisabledIcon(final int accessFlags) {
        if (ByteCodeUtils.isAnnotation(accessFlags)) {
            return Icons.ICON_ANNOTATION_DISABLED;
        } else if (ByteCodeUtils.isInterface(accessFlags)) {
            return Icons.ICON_INTERFACE_DISABLED;
        } else if (ByteCodeUtils.isEnum(accessFlags)) {
            return Icons.ICON_ENUM_DISABLED;
        } else {
            return Icons.ICON_CLASS_DISABLED;
        }
    }

    private static String[] getIconsGroup(final int accessFlags) {
        if (ByteCodeUtils.isAnnotation(accessFlags)) {
            return ANNOTATION_ICONS;
        } else if (ByteCodeUtils.isInterface(accessFlags)) {
            return INTERFACE_ICONS;
        } else if (ByteCodeUtils.isEnum(accessFlags)) {
            return ENUM_ICONS;
        } else {
            return CLASS_ICONS;
        }
    }

    @Override
    public int compareTo(final @NotNull ByteCodeTreeNode o) {
        if (o == null) {
            return 1;
        }
        final boolean thisPackage = isPackage();
        final boolean otherPackage = o.isPackage();
        if (thisPackage) {
            if (otherPackage) {
                return ((String) userObject).compareToIgnoreCase((String) o.getUserObject());
            } else {
                return -1;
            }
        } else {
            if (otherPackage) {
                return 1;
            } else {
                return ((String) userObject).compareToIgnoreCase((String) o.getUserObject());
            }
        }
    }
}
