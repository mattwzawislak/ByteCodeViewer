package org.obicere.bytecode.viewer.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.settings.handles.BooleanHandle;
import org.obicere.bytecode.viewer.settings.handles.ColorHandle;
import org.obicere.bytecode.viewer.settings.handles.DoubleHandle;
import org.obicere.bytecode.viewer.settings.handles.FloatHandle;
import org.obicere.bytecode.viewer.settings.handles.FontHandle;
import org.obicere.bytecode.viewer.settings.handles.Handle;
import org.obicere.bytecode.viewer.settings.handles.IntegerHandle;
import org.obicere.bytecode.viewer.settings.handles.LongHandle;
import org.obicere.bytecode.viewer.settings.handles.StringHandle;
import org.obicere.bytecode.viewer.settings.target.BooleanSetting;
import org.obicere.bytecode.viewer.settings.target.ColorSetting;
import org.obicere.bytecode.viewer.settings.target.DoubleSetting;
import org.obicere.bytecode.viewer.settings.target.FloatSetting;
import org.obicere.bytecode.viewer.settings.target.FontSetting;
import org.obicere.bytecode.viewer.settings.target.IntegerSetting;
import org.obicere.bytecode.viewer.settings.target.LongSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;
import org.obicere.bytecode.viewer.settings.target.StringSetting;
import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class SettingsController implements DomainAccess {

    private final Domain domain;

    private final Map<String, Handle<?>> handleMap = new HashMap<>();

    private final ReentrantLock readWriteLock = new ReentrantLock();

    private volatile Settings loadedSettings;

    private final Set<Group> groups = new LinkedHashSet<>();

    public SettingsController(final Domain domain) {
        this.domain = domain;

        addHandle(BooleanSetting.IDENTIFIER, new BooleanHandle());
        addHandle(ColorSetting.IDENTIFIER, new ColorHandle());
        addHandle(DoubleSetting.IDENTIFIER, new DoubleHandle());
        addHandle(FloatSetting.IDENTIFIER, new FloatHandle());
        addHandle(FontSetting.IDENTIFIER, new FontHandle());
        addHandle(IntegerSetting.IDENTIFIER, new IntegerHandle());
        addHandle(LongSetting.IDENTIFIER, new LongHandle());
        addHandle(StringSetting.IDENTIFIER, new StringHandle());
    }

    public void addHandle(final String settingClass, final Handle<?> handle) {
        if (settingClass == null) {
            throw new NullPointerException("setting class must be non-null.");
        }
        if (handle == null) {
            throw new NullPointerException("handle must be non-null.");
        }
        handleMap.put(settingClass, handle);
    }

    public Set<Group> getGroups() {
        return groups;
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

        final HashMap<String, Setting<?>> settings = loadSettingsFrom(groups);

        loadSetSettings(settings);

        this.loadedSettings = new Settings(settings);
    }

    private void safelySaveSettings() {

        final LinkedHashMap<String, String> output = new LinkedHashMap<>();

        // instead of iterating the settings map, we iterate by group
        // this allows the settings to remain sorted properly
        for (final Group group : groups) {
            for (final Setting<?> setting : group.getSettings()) {
                final String key = setting.getName();

                final Object setValue = setting.getValue();

                if (setValue == null || setValue.equals(setting.getDefaultValue())) {
                    continue;
                }

                final Handle<?> handler = handleMap.get(setting.getIdentifier());
                final String print = handler.encode(setValue);
                if (print != null) {
                    output.put(key, print);
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

    public boolean addSettingsGroup(final String className) {
        try {
            final Class<?> cls = Class.forName(className);
            if (!Group.class.isAssignableFrom(cls)) {
                Logger.getGlobal().log(Level.WARNING, "Class is not an instance of " + Group.class.getName());
                return false;
            }
            // this is technically checked as the class is assignable
            // to Group.class
            @SuppressWarnings("unchecked")
            final Group group = createGroup((Class<? extends Group>) cls);

            if (group != null) {
                groups.add(group);
                return true;
            }
        } catch (final ClassNotFoundException e) {
            Logger.getGlobal().log(Level.WARNING, "No class for group name: " + className);
        }
        return false;
    }

    private Group createGroup(final Class<? extends Group> groupClass) {
        Constructor<? extends Group> constructor = null;
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
        final Logger logger = domain.getLogger();
        final HashMap<String, Setting<?>> defaultsMap = new HashMap<>();
        for (final Group group : groups) {
            logger.log(Level.FINE, "Loaded group: " + group.getGroupName());

            final Setting<?>[] settings = group.getSettings();
            for (final Setting<?> setting : settings) {

                final String name = setting.getName();

                defaultsMap.put(name, setting);
                logger.log(Level.FINE, "Added setting: \"" + name + "\" as " + setting.getIdentifier());
            }
        }
        return defaultsMap;
    }

    private void loadSetSettings(final HashMap<String, Setting<?>> settings) {

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
                return;
            }

        } catch (final IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Failed to read settings.");
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
            return;
        }

        for (final Map.Entry<String, String> property : properties.entrySet()) {
            final Setting<?> setting = settings.get(property.getKey());
            if (setting == null) {
                continue;
            }
            final Handle<?> handle = handleMap.get(setting.getIdentifier());
            if (handle == null) {
                continue;
            }
            final String value = property.getValue();
            final Object decoded = handle.decode(value);
            if (decoded != null) {
                setting.setValue(decoded);
            }
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
