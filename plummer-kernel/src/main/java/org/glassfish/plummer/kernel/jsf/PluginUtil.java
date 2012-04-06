package org.glassfish.plummer.kernel.jsf;

import org.glassfish.plummer.kernel.PluginService;

/**
 *
 * @author jdlee
 */
public class PluginUtil {
    public static final String HABITAT_ATTRIBUTE = "org.glassfish.servlet.habitat";

    public static PluginService getPluginService() {
        return null; //getHabitat().getComponent(PluginService.class);
    }
}
