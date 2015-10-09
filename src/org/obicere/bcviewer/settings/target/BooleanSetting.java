package org.obicere.bcviewer.settings.target;

/**
 */
public class BooleanSetting extends Setting<Boolean> {

    public static final String MODELER_ID = "BooleanModeler";

    public BooleanSetting(final String name, final String descriptor, final Boolean value) {
        super(name, descriptor, value);
    }

    @Override
    public String getModelerID() {
        return MODELER_ID;
    }
}
