package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class _Testing {

    public static void main(final String[] args) {
        final Document document = new Document();
        document.add(new TextElement("e1", "Testing 123"));
        document.add(new TextElement("e2", "public int foo() {\n    return 5;\n}"));
        document.add(new TextElement("e3", "1") {
            @Override
            public boolean insertNewLine() {
                return false;
            }
        });
        document.add(new TextElement("e4", "2") {
            @Override
            public boolean insertNewLine() {
                return false;
            }
        });
        document.add(new TextElement("e5", "3") {
            @Override
            public boolean insertNewLine() {
                return false;
            }
        });

        final DocumentContent content = document.getContent();
        for (int i = 0; i < content.getLineCount(); i++) {
            System.out.print(i);
            System.out.print(':');
            System.out.println(content.getLine(i));
        }

        System.out.println(document.getRoot());
        System.out.println(document.getElement("e1"));
        System.out.println(document.getElement("e2"));
        System.out.println(document.getElement("e3"));
        System.out.println(document.getElement("e4"));
        System.out.println(document.getElement("e5"));

        System.out.println("\nSetting e3 to invisible");
        document.getElement("e3").setVisible(false);

        System.out.println(document.getRoot());
        System.out.println(document.getElement("e1"));
        System.out.println(document.getElement("e2"));
        System.out.println(document.getElement("e3"));
        System.out.println(document.getElement("e4"));
        System.out.println(document.getElement("e5"));

        System.out.println("\nCalled for document revalidation");
        document.getContent();

        System.out.println(document.getRoot());
        System.out.println(document.getElement("e1"));
        System.out.println(document.getElement("e2"));
        System.out.println(document.getElement("e3"));
        System.out.println(document.getElement("e4"));
        System.out.println(document.getElement("e5"));
    }

}
