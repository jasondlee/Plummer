/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.kernel;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.plummer.api.PluginFinder;
import org.glassfish.plummer.kernel.osgi.PluginTrackerImpl;

/**
 *
 * @author jdlee
 */
public class OsgiPluginFinder implements PluginFinder {

    @Override
    public Set<Class<?>> getClasses() {
        PluginTrackerImpl pt = (PluginTrackerImpl)PluginTrackerImpl.instance();
        Set<Class<?>> classes = new HashSet<Class<?>>();

        for (String className : pt.getClasses()) {
            try {
                Class c = //Class.forName(className);
                    Thread.currentThread().getContextClassLoader().loadClass(className);
                classes.add(c);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OsgiPluginFinder.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }

        return classes;
    }

    @Override
    public void release() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
