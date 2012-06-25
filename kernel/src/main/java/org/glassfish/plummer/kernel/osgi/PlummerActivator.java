/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.kernel.osgi;

import java.util.Properties;
import org.glassfish.plummer.api.PluginTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author jdlee
 */
public class PlummerActivator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(PluginTracker.class.getName(), PluginTrackerImpl.instance(), new Properties());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        context.ungetService(context.getServiceReference(PluginTracker.class.getName()));
    }

}
