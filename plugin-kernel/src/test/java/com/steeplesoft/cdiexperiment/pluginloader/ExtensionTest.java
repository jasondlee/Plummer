/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.cdiexperiment.pluginloader;

import com.steeplesoft.cdiexperiment.plugin.model.DataStore;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.Bean;
import junit.framework.Assert;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jdlee
 */
public class ExtensionTest {
    protected static WeldContainer weld;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("plugin.dir", "../example/plugin-sample1/target/");
        weld = new Weld().initialize();
    }
    
    @Test
    public void testPluginFinderLookup() {
        try {
            Class.forName("com.steeplesoft.cdiexperiment.pluginsample1.FileDataStore");
        } catch (ClassNotFoundException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    /*
    @Test
    public void findPlugins() {
        Set<Bean<?>> beans = weld.getBeanManager().getBeans(DataStore.class);
        assertFalse(beans.isEmpty());
    }
    * 
    */

    @Test
    public void findResources() {
        assertNotNull(getClass().getClassLoader().getResource("test.txt"));
    }
    
    @Test
    public void dummy() {
        
    }
}
