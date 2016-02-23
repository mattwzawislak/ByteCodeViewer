package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.PackageSpecifier;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;
import org.obicere.bytecode.viewer.settings.Settings;

/**
 */
public class PackageSpecifierModeler implements Modeler<PackageSpecifier> {
    @Override
    public void model(final PackageSpecifier element, final DocumentBuilder builder) {
        final Settings settings = builder.getDomain().getSettingsController().getSettings();
        final boolean importMode = settings.getBoolean("code.importMode", false);

        if (importMode) {
            return;
        }

        final String[] packageIdentifiers = element.getIdentifiers();
        for (final String identifier : packageIdentifiers) {
            builder.add(identifier);
            builder.add(".");
        }
    }
}
