package org.obicere.bcviewer;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.Field;
import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.utility.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Boot {

    private static Domain domain;

    private static final StartUpQueue QUEUE = new StartUpQueue();

    private static LinkedList<Object>                                                     list;
    private static LinkedList<?>                                                          list1;
    private static LinkedList<? super Object>                                             list2;
    private static LinkedList<? extends Number>                                           list3;
    private static LinkedList<? extends LinkedList<? super LinkedList<HashMap<?, Enum>>>> list4;

    public static void main(final String[] args) {
        // Prepare the current boot
        final long start = System.currentTimeMillis();
        prepareBoot();

        // Starting the actual boot sequence, everything needed for boot
        // should be loaded at this point
        final Logger logger = domain.getLogger();
        logger.info(domain.getApplicationName());
        logger.info("Starting boot.");

        // Run the start up tasks
        performStartUp(logger);

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            final GUIManager manager = domain.getGUIManager();
            final FrameManager frameManager = manager.getFrameManager();
            frameManager.loadDefaultTheme();
            frameManager.open();
            try {
                final File main = new File(".\\out\\production\\BytecodeViewer\\org\\obicere\\bcviewer\\bytecode");

                for (final File file : main.listFiles(e -> e.getName().endsWith(".class"))) {
                    final ClassInformation classInformation = new ClassInformation();

                    classInformation.loadFile(file);

                    System.out.print("outer most: ");
                    System.out.println(classInformation.getOuterMostClass().getName());
                    System.out.println();

                    for (final ClassFile cls : classInformation.getLoadedClasses()) {
                        System.out.println(cls.getName());
                        final ConstantPool pool = cls.getConstantPool();
                        /*
                        System.out.print("Major: ");
                        System.out.println(cls.getMajorVersion());
                        System.out.print("Minor: ");
                        System.out.println(cls.getMinorVersion());
                        System.out.print("Access: ");
                        for (final String access : BytecodeUtils.getClassAccessNames(cls.getAccessFlags())) {
                            System.out.print(access);
                            System.out.print(' ');
                        }
                        System.out.println();
                        System.out.print("Class: ");
                        System.out.println(pool.getAsString(cls.getThisClass()));
                        System.out.print("Super: ");
                        System.out.println(pool.getAsString(cls.getSuperClass()));
                        for (final int interfaceIndex : cls.getInterfaces()) {
                            System.out.print("Interface ");
                            System.out.print(interfaceIndex);
                            System.out.print(": ");
                            System.out.println(pool.getAsString(interfaceIndex));
                        }
                        System.out.println();
                        for (final Attribute attribute : cls.getAttributes()) {
                            System.out.println(attribute.toString(pool));
                        }
                        System.out.println();
                        */
                        for (final Field field : cls.getFields()) {
                            System.out.print("Field ");
                            System.out.print(pool.getAsString(field.getNameIndex()));
                            System.out.print("; ");
                            System.out.println(pool.getAsString(field.getDescriptorIndex()));
                            for (final Attribute attribute : field.getAttributes()) {
                                System.out.println(attribute.toString(pool));
                            }
                        }
                        System.out.println();
                        /*
                        for (final Method method : cls.getMethods()) {
                            System.out.println(method.toString(pool));
                        }
                        System.out.println();
                        */
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });

        logger.info("Boot time took (ms): " + (System.currentTimeMillis() - start));
    }

    public static Domain getGlobalDomain() {
        return domain;
    }

    public static StartUpQueue getStartUpQueue() {
        return QUEUE;
    }

    private static void prepareBoot() {

        setUpLogger();
        domain = new Domain();
        domain.initialize();
    }

    private static void setUpLogger() {
        LogManager.getLogManager().reset();

        final PrintFormatter formatter = new PrintFormatter();

        // Console support
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getGlobal().addHandler(consoleHandler);
    }

    private static void performStartUp(final Logger logger) {
        final long start = System.currentTimeMillis();
        logger.fine("Starting StartUp");

        QUEUE.runTasks(logger);
        QUEUE.cleanUp();

        logger.fine("StartUp took (ms): " + (System.currentTimeMillis() - start));
    }

}
