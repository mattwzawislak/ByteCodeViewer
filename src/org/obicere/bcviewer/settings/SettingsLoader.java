package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.settings.handles.BooleanHandle;
import org.obicere.bcviewer.settings.handles.ColorHandle;
import org.obicere.bcviewer.settings.handles.DoubleHandle;
import org.obicere.bcviewer.settings.handles.FloatHandle;
import org.obicere.bcviewer.settings.handles.FontHandle;
import org.obicere.bcviewer.settings.handles.Handle;
import org.obicere.bcviewer.settings.handles.IntegerHandle;
import org.obicere.bcviewer.settings.handles.LongHandle;
import org.obicere.bcviewer.settings.handles.StringHandle;
import org.obicere.bcviewer.settings.target.BooleanSetting;
import org.obicere.bcviewer.settings.target.ColorSetting;
import org.obicere.bcviewer.settings.target.DoubleSetting;
import org.obicere.bcviewer.settings.target.FloatSetting;
import org.obicere.bcviewer.settings.target.FontSetting;
import org.obicere.bcviewer.settings.target.IntegerSetting;
import org.obicere.bcviewer.settings.target.LongSetting;
import org.obicere.bcviewer.settings.target.Setting;
import org.obicere.bcviewer.settings.target.StringSetting;
import org.obicere.utility.reflect.Reflection;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class SettingsLoader {

    private final Map<Class<?>, Handle<?>> handleMap = new HashMap<>();

    private final ReentrantLock readWriteLock = new ReentrantLock();

    private volatile Settings loadedSettings;

    public SettingsLoader() {
        addHandle(BooleanSetting.class, new BooleanHandle());
        addHandle(BooleanSetting.class, new BooleanHandle());
        addHandle(ColorSetting.class, new ColorHandle());
        addHandle(DoubleSetting.class, new DoubleHandle());
        addHandle(FloatSetting.class, new FloatHandle());
        addHandle(FontSetting.class, new FontHandle());
        addHandle(IntegerSetting.class, new IntegerHandle());
        addHandle(LongSetting.class, new LongHandle());
        addHandle(StringSetting.class, new StringHandle());
    }

    public <T, H extends Handle<T>> void addHandle(final Class<? extends Setting<T>> settingClass, final H handle) {
        if (settingClass == null) {
            throw new NullPointerException("setting class must be non-null.");
        }
        if (handle == null) {
            throw new NullPointerException("handle must be non-null.");
        }
        handleMap.put(settingClass, handle);
    }

    public void loadSettings() {
        try {
            readWriteLock.lock();
            safelyLoadSettings();
        } finally {
            readWriteLock.unlock();
        }
    }

    private void safelyLoadSettings() {
        final Set<Group> groups = getGroups();

        final HashMap<String, Object> defaultsMap = loadDefaults(groups);
        final HashMap<String, Object> settings = loadSetSettings(groups);

        final Settings newSettings = new Settings(defaultsMap, settings);
        final Settings oldSettings = loadedSettings;

        if (oldSettings != null) {
            // transfer ownership of the property change listener
            newSettings.addPropertyChangeListener(oldSettings.getPropertyChangeListener());
        }

        this.loadedSettings = newSettings;
    }

    public void saveSettings() {
        try {
            readWriteLock.lock();
            safelySaveSettings();
        } finally {
            readWriteLock.unlock();
        }
    }

    private void safelySaveSettings() {

    }

    private Set<Group> getGroups() {
        final Set<Class<Group>> classes = Reflection.subclassOf(Group.class);
        final Set<Group> groups = new HashSet<>();

        for (final Class<Group> group : classes) {

            try {
                final Group instance = group.newInstance();
                groups.add(instance);

            } catch (final InstantiationException e) {
                Logger.getGlobal().log(Level.SEVERE, "Exception while instantiating group. Must have default constructor: " + group.getName());
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
            } catch (final IllegalAccessException e) {
                Logger.getGlobal().log(Level.SEVERE, "Cannot access default constructor of group: " + group.getName());
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return groups;
    }

    private HashMap<String, Object> loadDefaults(final Set<Group> groups) {
        final HashMap<String, Object> defaultsMap = new HashMap<>();
        for (final Group group : groups) {

            final Setting<?>[] settings = group.getSettings();

            for (final Setting<?> setting : settings) {

                final String name = setting.getName();
                final Object value = setting.getValue();

                defaultsMap.put(name, value);
            }
        }
        return defaultsMap;
    }

    private HashMap<String, Object> loadSetSettings(final Set<Group> groups) {
        final Properties properties = new Properties();
        // TODO: actually load them - need domain access
        final HashMap<String, Object> settings = new HashMap<>();

        for (final Group group : groups) {
            for (final Setting<?> setting : group.getSettings()) {
                final String key = setting.getName();
                final String get = properties.getProperty(key);
                if (get == null) {
                    continue;
                }
                final Object decoded = handleMap.get(setting.getClass()).decode(get);
                if (decoded != null) {
                    settings.put(key, decoded);
                }
            }
        }
        return settings;
    }
}
