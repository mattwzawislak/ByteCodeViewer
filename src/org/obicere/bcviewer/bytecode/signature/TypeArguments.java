package org.obicere.bcviewer.bytecode.signature;

import java.util.LinkedList;

/**
 */
public class TypeArguments {

    private final TypeArgument[] typeArguments;

    public TypeArguments(final TypeArgument[] typeArguments) {
        this.typeArguments = typeArguments;
    }

    public TypeArgument[] getTypeArguments() {
        return typeArguments;
    }

    public static TypeArguments parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final LinkedList<TypeArgument> typeArguments = new LinkedList<>();
        if (string.peek() == '<') {
            string.next();
            final TypeArgument firstArgument = TypeArgument.parse(string);
            if (firstArgument == null) {
                return null;
            }
            typeArguments.add(firstArgument);
            if (!string.hasNext('>')) {
                return null;
            }
            while (string.hasNext() && string.peek() != '>') {
                System.out.println(string);
                final TypeArgument typeArgument = TypeArgument.parse(string);
                if (typeArgument == null) {
                    return null;
                }
                typeArguments.add(typeArgument);
            }
            // consume the >
            string.next();
        }
        return new TypeArguments(typeArguments.toArray(new TypeArgument[typeArguments.size()]));
    }

}
