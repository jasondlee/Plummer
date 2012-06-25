/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.api;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author jdlee
 */
public class PluginActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        ServiceTracker tracker = new ServiceTracker(context, PluginTracker.class.getName(), null);
        tracker.open();
        PluginTracker pt = (PluginTracker)tracker.getService();
        if (pt != null) {
            pt.registerPluginBundle(context.getBundle());
        }
        tracker.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
