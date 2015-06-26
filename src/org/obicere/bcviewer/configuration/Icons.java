package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.context.Domain;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.AWTError;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

/**
 * The configuration is meant for handling system-specific settings and
 * resources. All of the loaded resources are static so an instance is
 * neither needed or accessible. The class itself replicates a utility
 * class, but pertains to information for I/O - which is part of the
 * configuration system.
 * <p>
 * Operating system information is also contained within the configuration.
 * As of v1.0 only the OS type is registered.
 *
 * @author Obicere
 * @version 1.0
 */

public class Icons {

    public static final String ICON_ANNOTATION_DEFAULT   = "annotation_default";
    public static final String ICON_ANNOTATION_DISABLED  = "annotation_disabled";
    public static final String ICON_ANNOTATION_PRIVATE   = "annotation_private";
    public static final String ICON_ANNOTATION_PROTECTED = "annotation_protected";
    public static final String ICON_ANNOTATION_PUBLIC    = "annotation_public";
    public static final String ICON_CLASS_DEFAULT        = "class_default";
    public static final String ICON_CLASS_DISABLED       = "class_disabled";
    public static final String ICON_CLASS_PRIVATE        = "class_private";
    public static final String ICON_CLASS_PROTECTED      = "class_protected";
    public static final String ICON_CLASS_PUBLIC         = "class_public";
    public static final String ICON_ENUM_DEFAULT         = "enum_default";
    public static final String ICON_ENUM_DISABLED        = "enum_disabled";
    public static final String ICON_ENUM_PRIVATE         = "enum_private";
    public static final String ICON_ENUM_PROTECTED       = "enum_protected";
    public static final String ICON_ENUM_PUBLIC          = "enum_public";
    public static final String ICON_FIELD_DEFAULT        = "field_default";
    public static final String ICON_FIELD_DISABLED       = "field_disabled";
    public static final String ICON_FIELD_PRIVATE        = "field_private";
    public static final String ICON_FIELD_PROTECTED      = "field_protected";
    public static final String ICON_FIELD_PUBLIC         = "field_public";
    public static final String ICON_INTERFACE_DEFAULT    = "interface_default";
    public static final String ICON_INTERFACE_DISABLED   = "interface_disabled";
    public static final String ICON_INTERFACE_PRIVATE    = "interface_private";
    public static final String ICON_INTERFACE_PROTECTED  = "interface_protected";
    public static final String ICON_INTERFACE_PUBLIC     = "interface_public";
    public static final String ICON_METHOD_DEFAULT       = "method_default";
    public static final String ICON_METHOD_DISABLED      = "method_disabled";
    public static final String ICON_METHOD_PRIVATE       = "method_private";
    public static final String ICON_METHOD_PROTECTED     = "method_protected";
    public static final String ICON_METHOD_PUBLIC        = "method_public";
    public static final String ICON_PACKAGE              = "package";
    public static final String ICON_PACKAGE_DISABLED     = "package_disabled";

    private final Domain domain;

    private final HashMap<String, Icon> cache = new HashMap<>();

    public Icons(final Domain domain) {
        this.domain = domain;
    }

    public synchronized Icon getIcon(final String name){
        final Icon get = cache.get(name);
        if(get == null){
            // Image is cached here
            return load(name);
        } else {
            return get;
        }
    }

    private Icon load(final String name) {
        // All icons need to be in a .gif format for now
        final String qualifiedName = domain.getPaths().getIconsDirectory() + name + ".gif";
        final Image image = loadImage(qualifiedName);
        final ImageIcon icon = new ImageIcon(image);

        // Cache the image here
        cache.put(name, icon);

        return icon;
    }

    /**
     * Loads the image from the specified URL through the default class
     * loader. This method has been tested and works on the following
     * formats:
     * <pre>
     *     png
     *     jpeg, jpeg2000
     *     gif
     * </pre>
     * <p>
     * The image is also prepared by the default {@link java.awt.Toolkit}
     *
     * @param url The URL to load the image from.
     * @return The loaded image, if successful. Otherwise
     * <code>null</code>.
     */

    private Image loadImage(final String url) {
        try {
            final Toolkit tk = Toolkit.getDefaultToolkit();
            final URL path = Icons.class.getClassLoader().getResource(url);
            final Image img = tk.createImage(path);
            tk.prepareImage(img, -1, -1, null);
            return img;
        } catch (final SecurityException e) {
            domain.getLogger().severe("Insufficient permissions to load image.");
            return null;
        } catch (final AWTError e) {
            domain.getLogger().severe("Could not instantiate default toolkit to load images.");
            return null;
        }
    }
}