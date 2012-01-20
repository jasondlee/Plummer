/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.cdiexperiment.pluginloader;

import com.steeplesoft.cdiexperiment.plugin.model.DataStore;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author jdlee
 */
public class ExtensionTest {

    protected static WeldContainer weld = new Weld().initialize();

    @Test
    public void findPlugins() {
        Set<Bean<?>> beans = weld.getBeanManager().getBeans(DataStore.class);
        assertEquals(2, beans.size());
    }
    
    @Test
    public void findResources() {
        assertNotNull(getClass().getClassLoader().getResource("test.txt"));
    }
}
