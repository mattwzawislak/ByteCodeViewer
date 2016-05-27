package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.io.LeafSource;
import org.obicere.bytecode.core.objects.ClassFile;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final LeafSource source;

    private final ClassFile classFile;

    private final Domain domain;

    public ClassInformation(final Domain domain, final ClassFile classFile, final LeafSource source) {
        this.domain = domain;
        this.source = source;
        this.classFile = classFile;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public LeafSource getSource() {
        return source;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
