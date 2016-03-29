package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.io.Source;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final Source source;

    private final ClassFile classFile;

    private final Domain domain;

    public ClassInformation(final Domain domain, final ClassFile classFile, final Source source) {
        this.domain = domain;
        this.source = source;
        this.classFile = classFile;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public Source getSource() {
        return source;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
