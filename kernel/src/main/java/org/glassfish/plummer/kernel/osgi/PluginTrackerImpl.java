/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.kernel.osgi;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.plummer.api.PluginTracker;
import org.osgi.framework.Bundle;

/**
 *
 * @author jdlee
 */
public class PluginTrackerImpl implements PluginTracker {
    private static PluginTracker instance;
    private Set<String> classes = new HashSet<String>();

    private PluginTrackerImpl() {
    }

    public static synchronized PluginTracker instance() {
        if (instance == null) {
            instance =  new PluginTrackerImpl();
        }

        return instance;
    }

    @Override
    public void registerPluginBundle(Bundle bundle) {
        if (bundle.getEntry("META-INF/beans.xml") != null) {
            Enumeration<URL> e = bundle.findEntries("/", "*.class", true);
            while (e.hasMoreElements()) {
                String className = e.nextElement().getPath().substring(1).replace("/", ".");
                className = className.substring(0, className.length()-6);
                classes.add(className);
//                try {
//                    Class c = bundle.loadClass(className);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(PluginTrackerImpl.class.getName()).
//                            log(Level.SEVERE, null, ex);
//                }
            }
        }
    }

    public Set<String> getClasses() {
        return classes;
    }
}
