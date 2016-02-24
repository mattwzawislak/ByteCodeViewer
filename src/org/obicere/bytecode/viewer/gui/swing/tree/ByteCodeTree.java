package org.obicere.bytecode.viewer.gui.swing.tree;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ByteCodeTree extends JTree {

    private ByteCodeTreeNode root;

    private final DefaultTreeModel model;

    private final ReentrantLock addRemoveLock = new ReentrantLock();

    private final Domain domain;

    public ByteCodeTree(final Domain domain) {
        this.domain = domain;
        this.model = (DefaultTreeModel) getModel();

        setCellRenderer(new ByteCodeTreeCellRenderer());
        addTreeSelectionListener(e -> {

            final TreePath path = e.getPath();
            final Object[] userPath = path.getPath();
            final StringBuilder className = new StringBuilder();

            boolean first = true;
            // we start at 1 to avoid the empty root node
            for (int i = 1; i < userPath.length; i++) {
                final Object userObject = userPath[i];
                if (!first) {
                    className.append('.');
                }
                className.append(userObject);
                first = false;
            }

            domain.getGUIManager().getFrameManager().getEditorManager().displayEditorPanel(className.toString());
        });
    }

    public void addClass(final ClassFile file) {
        addClass(file, file.getAccessFlags());
    }

    public void addClass(final ClassFile file, final int accessFlags) {
        final String className = ByteCodeUtils.getClassName(file.getName());
        final String packageName = ByteCodeUtils.getPackage(file.getName());

        try {
            if (root == null) {
                root = createPackagePart("Classes");

                model.setRoot(root);
            }

            addRemoveLock.lock();
            final ByteCodeTreeNode possiblePackage = getPackage(packageName);
            final ByteCodeTreeNode absolutePackage;
            if (possiblePackage == null) {
                absolutePackage = addPackage(packageName);
            } else {
                absolutePackage = possiblePackage;
            }

            if (hasChildByName(absolutePackage, className)) {
                return;
            }
            final ByteCodeTreeNode node = ByteCodeTreeNode.buildNode(domain, file, accessFlags);

            model.insertNodeInto(node, absolutePackage, absolutePackage.getIndexFor(node));
            expandPath(new TreePath(root.getPath()));
        } finally {
            addRemoveLock.unlock();
        }
    }

    private ByteCodeTreeNode addPackage(final String name) {
        final String[] split = name.split("\\.");
        ByteCodeTreeNode root = this.root;

        int i = 0;
        while (i < split.length) {
            final ByteCodeTreeNode node = getPackagePart(root, split[i]);
            if (node == null) {
                break;
            }
            root = node;
            i++;
        }
        while (i < split.length) {
            root = addPackagePart(root, split[i++]);
        }
        return root;
    }

    private ByteCodeTreeNode addPackagePart(final ByteCodeTreeNode node, final String name) {
        if (hasChildByName(node, name)) {
            return null;
        }
        final ByteCodeTreeNode packageNode = createPackagePart(name);

        model.insertNodeInto(packageNode, node, node.getIndexFor(packageNode));

        return packageNode;
    }

    private ByteCodeTreeNode createPackagePart(final String name) {
        final Icons icons = domain.getIcons();
        return new ByteCodeTreeNode(icons.getIcon(Icons.ICON_PACKAGE), icons.getIcon(Icons.ICON_PACKAGE_DISABLED), name, true);
    }

    private boolean hasChildByName(final ByteCodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final ByteCodeTreeNode next = (ByteCodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return true;
            }
        }
        return false;
    }

    public ByteCodeTreeNode getPackage(final String name) {
        try {
            addRemoveLock.lock();
            if (root == null) {
                return null;
            }
            if (name == null || name.isEmpty()) {
                return root;
            }
            final String[] split = name.split("\\.");
            ByteCodeTreeNode root = this.root;
            for (final String item : split) {
                final ByteCodeTreeNode node = getPackagePart(root, item);
                if (node == null) {
                    return null;
                }
                root = node;
            }
            return root;
        } finally {
            addRemoveLock.unlock();
        }
    }

    private ByteCodeTreeNode getPackagePart(final ByteCodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final ByteCodeTreeNode next = (ByteCodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return next;
            }
        }
        return null;
    }
}
