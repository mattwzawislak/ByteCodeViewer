package org.obicere.bcviewer.bytecode.signature;

import java.util.LinkedList;

/**
 */
public class ClassTypeSignature extends ReferenceTypeSignature {

    private final PackageSpecifier           packageSpecifier;
    private final SimpleClassTypeSignature   simpleClassTypeSignature;
    private final ClassTypeSignatureSuffix[] classTypeSignatureSuffix;

    private ClassTypeSignature(final PackageSpecifier packageSpecifier, final SimpleClassTypeSignature simpleClassTypeSignature, final ClassTypeSignatureSuffix[] classTypeSignatureSuffix) {
        this.packageSpecifier = packageSpecifier;
        this.simpleClassTypeSignature = simpleClassTypeSignature;
        this.classTypeSignatureSuffix = classTypeSignatureSuffix;
    }

    public PackageSpecifier getPackageSpecifier() {
        return packageSpecifier;
    }

    public SimpleClassTypeSignature getSimpleClassTypeSignature() {
        return simpleClassTypeSignature;
    }

    public ClassTypeSignatureSuffix[] getClassTypeSignatureSuffix() {
        return classTypeSignatureSuffix;
    }

    public static ClassTypeSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != 'L') {
            return null;
        }
        final PackageSpecifier packageSpecifier = PackageSpecifier.parse(string);
        if (packageSpecifier == null) {
            return null;
        }
        final SimpleClassTypeSignature simpleClassTypeSignature = SimpleClassTypeSignature.parse(string);
        if (simpleClassTypeSignature == null) {
            return null;
        }
        if (!string.hasNext(';')) {
            return null;
        }
        final LinkedList<ClassTypeSignatureSuffix> suffixList = new LinkedList<>();
        char next = string.peek();
        while (string.hasNext() && next != ';') {
            final ClassTypeSignatureSuffix suffix = ClassTypeSignatureSuffix.parse(string);
            if (suffix == null) {
                return null;
            }
            suffixList.add(suffix);
            next = string.peek();
        }
        if (string.next() != ';') {
            return null;
        }
        return new ClassTypeSignature(packageSpecifier, simpleClassTypeSignature, suffixList.toArray(new ClassTypeSignatureSuffix[suffixList.size()]));
    }

}
