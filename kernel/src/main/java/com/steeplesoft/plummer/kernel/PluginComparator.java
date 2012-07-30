package com.steeplesoft.plummer.kernel;

import java.util.Comparator;

/**
 *
 * @author jasonlee
 */
public class PluginComparator implements Comparator<PluginMetadata> {
    @Override
    public int compare(PluginMetadata cp1, PluginMetadata cp2) {
        return cp1.getPriority() - cp2.getPriority();
    }
}
