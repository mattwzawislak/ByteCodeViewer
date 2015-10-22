package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.context.Domain;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.AWTError;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;

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

    public static final String ICON_ANNOTATION_DEFAULT   = "annotation_default.gif";
    public static final String ICON_ANNOTATION_DISABLED  = "annotation_disabled.gif";
    public static final String ICON_ANNOTATION_PRIVATE   = "annotation_private.gif";
    public static final String ICON_ANNOTATION_PROTECTED = "annotation_protected.gif";
    public static final String ICON_ANNOTATION_PUBLIC    = "annotation_public.gif";
    public static final String ICON_CLASS_DEFAULT        = "class_default.gif";
    public static final String ICON_CLASS_DISABLED       = "class_disabled.gif";
    public static final String ICON_CLASS_PRIVATE        = "class_private.gif";
    public static final String ICON_CLASS_PROTECTED      = "class_protected.gif";
    public static final String ICON_CLASS_PUBLIC         = "class_public.gif";
    public static final String ICON_ENUM_DEFAULT         = "enum_default.gif";
    public static final String ICON_ENUM_DISABLED        = "enum_disabled.gif";
    public static final String ICON_ENUM_PRIVATE         = "enum_private.gif";
    public static final String ICON_ENUM_PROTECTED       = "enum_protected.gif";
    public static final String ICON_ENUM_PUBLIC          = "enum_public.gif";
    public static final String ICON_FIELD_DEFAULT        = "field_default.gif";
    public static final String ICON_FIELD_DISABLED       = "field_disabled.gif";
    public static final String ICON_FIELD_PRIVATE        = "field_private.gif";
    public static final String ICON_FIELD_PROTECTED      = "field_protected.gif";
    public static final String ICON_FIELD_PUBLIC         = "field_public.gif";
    public static final String ICON_INTERFACE_DEFAULT    = "interface_default.gif";
    public static final String ICON_INTERFACE_DISABLED   = "interface_disabled.gif";
    public static final String ICON_INTERFACE_PRIVATE    = "interface_private.gif";
    public static final String ICON_INTERFACE_PROTECTED  = "interface_protected.gif";
    public static final String ICON_INTERFACE_PUBLIC     = "interface_public.gif";
    public static final String ICON_METHOD_DEFAULT       = "method_default.gif";
    public static final String ICON_METHOD_DISABLED      = "method_disabled.gif";
    public static final String ICON_METHOD_PRIVATE       = "method_private.gif";
    public static final String ICON_METHOD_PROTECTED     = "method_protected.gif";
    public static final String ICON_METHOD_PUBLIC        = "method_public.gif";
    public static final String ICON_PACKAGE              = "package.gif";
    public static final String ICON_PACKAGE_DISABLED     = "package_disabled.gif";

    public static final String ICON_CLOSE       = "close.gif";
    public static final String ICON_CLOSE_HOVER = "close_hover.gif";

    public static final String ICON_DARK_16  = "dark_16.png";
    public static final String ICON_DARK_20  = "dark_20.png";
    public static final String ICON_DARK_24  = "dark_24.png";
    public static final String ICON_DARK_32  = "dark_32.png";
    public static final String ICON_DARK_40  = "dark_40.png";
    public static final String ICON_DARK_48  = "dark_48.png";
    public static final String ICON_DARK_64  = "dark_64.png";
    public static final String ICON_DARK_128 = "dark_128.png";
    public static final String ICON_DARK_256 = "dark_256.png";
    public static final String ICON_DARK_512 = "dark_512.png";

    private static final String[] ICON_DARK_ARRAY = new String[]{
            ICON_DARK_16,
            ICON_DARK_20,
            ICON_DARK_24,
            ICON_DARK_32,
            ICON_DARK_40,
            ICON_DARK_48,
            ICON_DARK_64,
            ICON_DARK_128,
            ICON_DARK_256,
            ICON_DARK_512
    };

    private final Domain domain;

    private final HashMap<String, Icon> iconCache = new HashMap<>();

    private final HashMap<String, Image> imageCache = new HashMap<>();

    public Icons(final Domain domain) {
        this.domain = domain;
    }

    public Icon getIcon(final String name) {
        final Icon get = iconCache.get(name);
        if (get == null) {
            // Image is cached here
            load(name);
            return iconCache.get(name);
        } else {
            return get;
        }
    }

    public Image getImage(final String name) {
        final Image get = imageCache.get(name);
        if (get == null) {
            load(name);
            return imageCache.get(name);
        } else {
            return get;
        }
    }

    public Image[] getDarkApplicationImages() {
        final String[] array = ICON_DARK_ARRAY;
        final Image[] images = new Image[array.length];

        for (int i = 0; i < array.length; i++) {
            final String element = array[i];
            images[i] = getImage(element);
        }
        return images;
    }

    private void load(final String name) {
        final String qualifiedName = domain.getPaths().getIconsDirectory() + name;
        final Image image = loadImage(qualifiedName);
        final ImageIcon icon = new ImageIcon(image);

        // Cache the image here
        iconCache.put(name, icon);
        imageCache.put(name, image);
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
            final InputStream path = getClass().getResourceAsStream(url);
            final BufferedImage image = ImageIO.read(path);
            tk.prepareImage(image, -1, -1, null);
            return image;
        } catch (final IOException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        } catch (final SecurityException e) {
            domain.getLogger().severe("Insufficient permissions to load image.");
        } catch (final AWTError e) {
            domain.getLogger().severe("Could not instantiate default toolkit to load images.");
        }
        return null;
    }
}