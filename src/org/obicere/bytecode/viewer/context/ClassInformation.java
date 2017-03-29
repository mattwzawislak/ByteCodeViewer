package org.obicere.bytecode.viewer.context;

import org.javacore.JCClass;
import org.obicere.bytecode.core.io.LeafSource;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private final LeafSource source;

    private final JCClass classFile;

    private final Domain domain;

    public ClassInformation(final Domain domain, final JCClass classFile, final LeafSource source) {
        this.domain = domain;
        this.source = source;
        this.classFile = classFile;
    }

    public JCClass getClassFile() {
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
