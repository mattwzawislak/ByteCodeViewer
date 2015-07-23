package org.obicere.bcviewer;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.bytecode.Field;
import org.obicere.bcviewer.bytecode.Method;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.reader.ClassFileReader;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.utility.io.IOUtils;
import org.obicere.utility.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Boot {

    private static Domain domain;

    private static final StartUpQueue QUEUE = new StartUpQueue();

    public static void main(final String[] args) {
        // Prepare the current boot
        final long start = System.currentTimeMillis();
        prepareBoot();

        // Starting the actual boot sequence, everything needed for boot
        // should be loaded at this point
        final Logger logger = domain.getLogger();
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
                final File main = new File("F:\\Programming\\BytecodeViewer\\out\\production\\BytecodeViewer\\org\\obicere\\bcviewer\\bytecode");
                final File[] files = main.listFiles(pathname -> pathname.getName().endsWith(".class"));
                if(files != null){
                    for(final File file : files) {
                        final ClassFile cls = new ClassFileReader().read(new IndexedDataInputStream(IOUtils.readData(file)));
                        System.out.println(file);
                        final ConstantPool pool = cls.getConstantPool();
                        System.out.print("major: ");
                        System.out.println(cls.getMajorVersion());
                        System.out.print("minor: ");
                        System.out.println(cls.getMinorVersion());
                        System.out.print("access: ");
                        System.out.println(Integer.toHexString(cls.getAccessFlags()));
                        System.out.print("class: ");
                        System.out.println(pool.getAsString(cls.getThisClass()));
                        System.out.print("super: ");
                        System.out.println(pool.getAsString(cls.getSuperClass()));
                        for(final int interfaceIndex : cls.getInterfaces()){
                            System.out.print("Interface ");
                            System.out.print(interfaceIndex);
                            System.out.print(": ");
                            System.out.println(pool.getAsString(interfaceIndex));
                        }
                        System.out.println();
                        for(final Field field : cls.getFields()){
                            System.out.print("Field ");
                            System.out.print(pool.getAsString(field.getNameIndex()));
                            System.out.print("; ");
                            System.out.println(pool.getAsString(field.getDescriptorIndex()));
                        }
                        System.out.println();
                        for(final Method method : cls.getMethods()){
                            System.out.println(method.toString(pool));
                        }
                        System.out.println();
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
