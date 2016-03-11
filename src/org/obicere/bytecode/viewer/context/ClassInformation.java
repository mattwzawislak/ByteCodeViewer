package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.util.IndexedDataInputStream;
import org.obicere.bytecode.viewer.io.Source;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ClassInformation implements DomainAccess {

    private Source fileSource;

    private ClassFile rootClass;

    private final Domain domain;

    public ClassInformation(final Domain domain) {
        this.domain = domain;
    }

    public ClassFile getRootClass() {
        return rootClass;
    }

    public Source getFileSource() {
        return fileSource;
    }

    public void clear() {
        rootClass = null;
    }

    public ClassFile load(final Source fileSource, final byte[] classBytes) throws IOException {
        final IndexedDataInputStream stream = new IndexedDataInputStream(classBytes);

        this.fileSource = fileSource;
        this.rootClass = domain.getClassReader().read(stream);

        return rootClass;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
