package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class BytecodeTree extends JTree {

    private BytecodeTreeNode root;

    private DefaultTreeModel model;

    private ReentrantLock addRemoveLock = new ReentrantLock();

    private final Domain domain;

    public BytecodeTree(final Domain domain) {
        this.domain = domain;
        this.model = (DefaultTreeModel) getModel();

        setCellRenderer(new BytecodeTreeCellRenderer());
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
        final String className = BytecodeUtils.getClassName(file.getName());
        final String packageName = BytecodeUtils.getPackage(file.getName());

        try {
            if (root == null) {
                root = createPackagePart("");

                model.setRoot(root);
            }

            addRemoveLock.lock();
            final BytecodeTreeNode possiblePackage = getPackage(packageName);
            final BytecodeTreeNode absolutePackage;
            if (possiblePackage == null) {
                absolutePackage = addPackage(packageName);
            } else {
                absolutePackage = possiblePackage;
            }

            if (hasChildByName(absolutePackage, className)) {
                return;
            }
            final BytecodeTreeNode node = BytecodeTreeNode.buildNode(domain, file, accessFlags);

            SwingUtilities.invokeLater(() -> model.insertNodeInto(node, absolutePackage, absolutePackage.getIndexFor(node)));
        } finally {
            addRemoveLock.unlock();
        }
    }

    private BytecodeTreeNode addPackage(final String name) {
        final String[] split = name.split("\\.");
        BytecodeTreeNode root = this.root;

        int i = 0;
        while (i < split.length) {
            final BytecodeTreeNode node = getPackagePart(root, split[i]);
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

    private BytecodeTreeNode addPackagePart(final BytecodeTreeNode node, final String name) {
        if (hasChildByName(node, name)) {
            return null;
        }
        final BytecodeTreeNode packageNode = createPackagePart(name);

        model.insertNodeInto(packageNode, node, node.getIndexFor(packageNode));

        return packageNode;
    }

    private BytecodeTreeNode createPackagePart(final String name) {
        final Icons icons = domain.getIcons();
        return new BytecodeTreeNode(icons.getIcon(Icons.ICON_PACKAGE), icons.getIcon(Icons.ICON_PACKAGE_DISABLED), name, true);
    }

    private boolean hasChildByName(final BytecodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final BytecodeTreeNode next = (BytecodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return true;
            }
        }
        return false;
    }

    public BytecodeTreeNode getPackage(final String name) {
        try {
            addRemoveLock.lock();
            if (root == null) {
                return null;
            }
            if (name == null) {
                return root;
            }
            final String[] split = name.split("\\.");
            BytecodeTreeNode root = this.root;
            for (final String item : split) {
                final BytecodeTreeNode node = getPackagePart(root, item);
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

    private BytecodeTreeNode getPackagePart(final BytecodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final BytecodeTreeNode next = (BytecodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return next;
            }
        }
        return null;
    }
}
