package org.obicere.bcviewer.gui.settings;

import org.obicere.bcviewer.settings.target.Setting;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class SettingModelFactory<C> {

    private final Map<String, SettingModeler<?, C>> modelers = new HashMap<>();

    public void addModeler(final String id, final SettingModeler<?, C> modeler) {
        if (id == null) {
            throw new NullPointerException("modeler id must be non-null.");
        }

        if (modeler == null) {
            throw new NullPointerException("modeler must be non-null.");
        }
        modelers.put(id, modeler);
    }

    @SuppressWarnings("unchecked")
    public <T> C model(final Setting<T> setting) {
        if (setting == null) {
            throw new NullPointerException("setting must be non-null.");
        }
        final SettingModeler<T, C> modeler = (SettingModeler<T, C>) modelers.get(setting.getID());
        if (modeler == null) {
            return null;
        }
        return modeler.model(setting);
    }

}
