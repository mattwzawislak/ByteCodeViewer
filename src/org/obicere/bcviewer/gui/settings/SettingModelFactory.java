package org.obicere.bcviewer.gui.settings;

import org.obicere.bcviewer.settings.target.Setting;

import java.util.HashMap;

/**
 */
public class SettingModelFactory<C> {

    private HashMap<Class<? extends Setting>, SettingModeler<?, C>> modelers = new HashMap<>();

    public <T> void addModeler(final Class<? extends Setting<T>> cls, final SettingModeler<T, C> modeler) {
        if (cls == null) {
            throw new NullPointerException("class must be non-null.");
        }

        if (modeler == null) {
            throw new NullPointerException("modeler must be non-null.");
        }
        modelers.put(cls, modeler);
    }

    @SuppressWarnings("unchecked")
    public <T> C model(final Setting<T> setting) {
        if (setting == null) {
            throw new NullPointerException("setting must be non-null.");
        }
        final SettingModeler<T, C> modeler = (SettingModeler<T, C>) modelers.get(setting.getClass());
        if (modeler == null) {
            return null;
        }
        return modeler.model(setting);
    }

}
