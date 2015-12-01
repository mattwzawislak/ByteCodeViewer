package org.obicere.bytecode.viewer.settings.application;

import org.obicere.bytecode.viewer.settings.Group;
import org.obicere.bytecode.viewer.settings.target.BooleanSetting;
import org.obicere.bytecode.viewer.settings.target.IntegerSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;

/**
 */
public class CodeGroup implements Group {

    private static final String NAME = "Code";

    private static final String SUFFIX = "code.";

    private static final IntegerSetting TAB_SIZE = new IntegerSetting(SUFFIX + "tabSize", "Tab size", 4, 1, Integer.MAX_VALUE);

    private static final BooleanSetting INCLUDE_CONSTANT_POOL = new BooleanSetting(SUFFIX + "includeConstantPool", "Include Constant Pool", false);
    private static final BooleanSetting EXTENDS_OBJECT        = new BooleanSetting(SUFFIX + "extendsObject", "Show extends Object", false);
    private static final BooleanSetting IMPORT_MODE           = new BooleanSetting(SUFFIX + "importMode", "Show imports instead of qualified names", true);

    private static final Setting<?>[] SETTINGS = new Setting[]{
            TAB_SIZE,
            INCLUDE_CONSTANT_POOL,
            EXTENDS_OBJECT,
            IMPORT_MODE
    };

    @Override
    public String getGroupName() {
        return NAME;
    }

    @Override
    public Setting<?>[] getSettings() {
        return SETTINGS;
    }
}
