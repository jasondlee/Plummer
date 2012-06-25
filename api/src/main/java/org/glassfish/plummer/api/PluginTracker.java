/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.api;

import org.osgi.framework.Bundle;

/**
 *
 * @author jdlee
 */
public interface PluginTracker {
    void registerPluginBundle(Bundle bundle);
}
