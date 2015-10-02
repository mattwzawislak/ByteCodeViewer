package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
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
import org.obicere.bcviewer.util.FileUtils;
import org.obicere.utility.reflect.Reflection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class SettingsController implements DomainAccess {

    private final Domain domain;

    private final Map<Class<?>, Handle<?>> handleMap = new HashMap<>();

    private final ReentrantLock readWriteLock = new ReentrantLock();

    private volatile Settings loadedSettings;

    // correspond to the loaded settings instance and only that instance
    private volatile Set<Group> groups;

    public SettingsController(final Domain domain) {
        this.domain = domain;

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

    public Settings getSettings() {
        try {
            readWriteLock.lock();
            return loadedSettings;
        } finally {
            readWriteLock.unlock();
        }
    }

    public void loadSettings() {
        try {
            readWriteLock.lock();
            safelyLoadSettings();
        } finally {
            readWriteLock.unlock();
        }
    }

    public void saveSettings() {
        try {
            readWriteLock.lock();
            safelySaveSettings();
        } finally {
            readWriteLock.unlock();
        }
    }

    private void safelyLoadSettings() {
        final Set<Group> groups = getGroups();

        final HashMap<String, Setting<?>> defaultsMap = loadSettingsFrom(groups);
        final HashMap<String, Object> settings = loadSetSettings(groups);

        final Settings newSettings = new Settings(defaultsMap, settings);
        final Settings oldSettings = loadedSettings;

        if (oldSettings != null) {
            // transfer ownership of the property change listener
            newSettings.addPropertyChangeListener(oldSettings.getPropertyChangeListener());
        }

        this.groups = groups;
        this.loadedSettings = newSettings;
    }

    private void safelySaveSettings() {
        final HashMap<String, Setting<?>> settings = loadedSettings.getSettings();
        final HashMap<String, Object> current = loadedSettings.getCurrentMap();

        final LinkedHashMap<String, String> output = new LinkedHashMap<>();

        // instead of iterating the settings map, we iterate by group
        // this allows the settings to remain sorted properly
        for (final Group group : groups) {
            for (final Setting<?> setting : group.getSettings()) {
                final String key = group.getGroupName() + "." + setting.getName();

                final Object defaultValue = settings.get(key).getValue();
                final Object setValue = current.get(key);

                final Handle<?> handler = handleMap.get(setting.getClass());

                if (setValue == null) {
                    continue;
                }
                if (!setValue.equals(defaultValue)) {
                    final String print = handler.encode(setValue);
                    if (print != null) {
                        output.put(key, print);
                    }
                }
            }
        }

        final File settingsFile = new File(domain.getPaths().getSettingsFile());

        try {
            FileUtils.writeProperties(output, settingsFile);
        } catch (final IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Failed to save settings.");
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
        }
    }

    private Set<Group> getGroups() {
        // this really could be changed to be stored in some file
        final Set<Class<Group>> classes = Reflection.subclassOf(Group.class);
        final Set<Group> groups = new HashSet<>();

        for (final Class<Group> group : classes) {
            final Group newGroup = createGroup(group);
            if (newGroup != null) {
                groups.add(newGroup);
            }
        }
        return groups;
    }

    private Group createGroup(final Class<Group> groupClass) {
        Constructor<Group> constructor = null;
        boolean hasDomain = false;

        try {
            constructor = groupClass.getConstructor();
        } catch (final NoSuchMethodException e) {
            // ignore, this can happen
        }
        if (constructor == null) {
            try {
                constructor = groupClass.getConstructor(Domain.class);
                hasDomain = true;

            } catch (final NoSuchMethodException e) {
                final String simpleName = groupClass.getSimpleName();
                Logger.getGlobal().log(Level.WARNING, "Exception while instantiating group: " + groupClass.getName());
                Logger.getGlobal().log(Level.WARNING, "Must have one of these constructors: " + simpleName + "() or " + simpleName + "(Domain)");
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);

                return null;
            }
        }

        try {
            constructor.setAccessible(true);
            if (hasDomain) {
                return constructor.newInstance(domain);
            } else {
                return constructor.newInstance();
            }
        } catch (final InstantiationException e) {
            Logger.getGlobal().log(Level.WARNING, "Cannot instantiate abstract class: " + groupClass.getName());
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);

        } catch (final InvocationTargetException e) {
            Logger.getGlobal().log(Level.WARNING, "Exception while instantiating group: " + groupClass.getName());
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);

        } catch (final IllegalAccessException e) {
            Logger.getGlobal().log(Level.WARNING, "Cannot access default constructor of group: " + groupClass.getName());
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
        }
        return null;
    }

    private HashMap<String, Setting<?>> loadSettingsFrom(final Set<Group> groups) {
        final HashMap<String, Setting<?>> defaultsMap = new HashMap<>();
        for (final Group group : groups) {

            final Setting<?>[] settings = group.getSettings();

            for (final Setting<?> setting : settings) {

                final String name = group.getGroupName() + "." + setting.getName();

                defaultsMap.put(name, setting);
            }
        }
        return defaultsMap;
    }

    private HashMap<String, Object> loadSetSettings(final Set<Group> groups) {

        final Map<String, String> properties;
        try {
            final File settingsFile = new File(domain.getPaths().getSettingsFile());
            if (settingsFile.exists()) {
                properties = FileUtils.readProperties(settingsFile);
            } else {
                Logger.getGlobal().log(Level.INFO, "No settings file. Created one.");
                if (!settingsFile.createNewFile()) {
                    Logger.getGlobal().log(Level.WARNING, "Failed to create settings file: " + settingsFile);
                }
                // return an empty hash map, as this technically didn't
                // blow up in a ball of fire and smoke
                return new HashMap<>();
            }

        } catch (final IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Failed to read settings.");
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
            return new HashMap<>();
        }

        final HashMap<String, Object> settings = new HashMap<>();

        for (final Group group : groups) {
            for (final Setting<?> setting : group.getSettings()) {
                final String key = group.getGroupName() + "." + setting.getName();
                final String get = properties.get(key);
                if (get == null) {
                    continue;
                }
                final Object decoded = handleMap.get(setting.getClass()).decode(get);
                if (decoded != null) {
                    settings.put(key, decoded);
                    final PropertyChangeListener listener = setting.getPropertyChangeListener();
                    if (listener != null) {
                        listener.propertyChange(new PropertyChangeEvent(setting, key, setting.getValue(), decoded));
                    }
                }
            }
        }
        return settings;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
