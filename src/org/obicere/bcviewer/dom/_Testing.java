package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class _Testing {

    public static void main(final String[] args) {
        final Document document = new Document();
        final Element root = document.getRoot();

        root.add(new TextElement("l1", "public class Test {"));
        root.add(new TextElement("l2", "\t"));
        root.add(new TextElement("l3", "\tpublic void foo(){"));
        root.add(new TextElement("l4", "\t\tSystem.out.println(123);"));
        root.add(new TextElement("l5", "\t}"));
        root.add(new TextElement("l6", "}"));

        for(int i = 4; i <= 8; i++){
            System.out.println("Testing tab size: " + i);
            document.setTabSize(i);
            final DocumentContent content = document.getContent();
            for(int line = 0; line < content.getLineCount(); line++){
                System.out.print(line);
                System.out.print(':');
                System.out.println(content.getLine(line));
            }
        }
    }

}
