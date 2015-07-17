package org.obicere.bcviewer;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author Obicere
 */
public class ReaderGenerator {

    private static final String endl = System.getProperty("line.separator");

    private static final String str =
            "package org.obicere.bcviewer.reader.instruction;" + endl +
            "" + endl +
            "import org.obicere.bcviewer.bytecode.instruction.$name;" + endl +
            "import org.obicere.bcviewer.util.IndexedDataInputStream;" + endl +
            "import org.obicere.bcviewer.util.Reader;" + endl +
            "" + endl +
            "import java.io.IOException;" + endl +
            "" + endl +
            "/**" + endl +
            " * @author Obicere" + endl +
            " */" + endl +
            "public class $nameReader implements Reader<$name> {" + endl +
            "" + endl +
            "    private final $name instance = new $name();" + endl +
            "" + endl +
            "    @Override" + endl +
            "    public $name read(final IndexedDataInputStream input) throws IOException {" + endl +
            "        return instance;" + endl +
            "    }" + endl +
            "}";

    public static void main(final String[] args) {
        final File file = new File("F:\\Programming\\BytecodeViewer\\src\\org\\obicere\\bcviewer\\reader\\instruction");

        final File[] files = file.listFiles();

        for (final File next : files) {
            final String name = next.getName();
            final String instr = name.replace("Reader.java", "");
            final char c = name.charAt(0);
            switch (c) {
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                    continue;
            }
            try {
                final PrintWriter writer = new PrintWriter(new File(file, name));
                writer.print(str.replace("$name", instr));
                writer.flush();
                writer.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }

        }
    }

}
