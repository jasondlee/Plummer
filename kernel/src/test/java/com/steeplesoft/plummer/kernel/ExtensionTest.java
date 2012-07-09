/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.kernel;

import com.steeplesoft.plummer.example.model.DataStore;
import com.steeplesoft.plummer.example.model.Marker;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import junit.framework.Assert;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author jdlee
 */
@Ignore
public class ExtensionTest {
    protected static WeldContainer weld;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("plugin.dir", "../examples/plummer-sample1/target/");
        weld = new Weld().initialize();
    }
    
    @Test
    public void testPluginFinderLookup() {
        try {
            Class.forName("com.steeplesoft.plummer.example.sample1.FileDataStore");
        } catch (ClassNotFoundException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void findPlugins() {
        Set<Bean<?>> beans = weld.getBeanManager().getBeans(DataStore.class);
        System.out.println(beans);
        System.out.println(beans.size());
        Assert.assertFalse(beans.isEmpty());
    }
    
    @Test
    public void testFileDataStore() {
        DataStore fds = lookupBean(DataStore.class);
        System.out.println(fds);
        System.out.println(fds.getDescription());
    }

    @Test
    public void findResources() {
        assertNotNull(getClass().getClassLoader().getResource("test.txt"));
    }
    
    @Test
    public void testInjection() {
//        final BeanManager beanManager = weld.getBeanManager();
//        Set<Bean<?>> beans = beanManager.getBeans(Marker.class);
//        Bean bean = beans.iterator().next();
//        CreationalContext cc = beanManager.createCreationalContext(bean);
//        Object o = beanManager.getReference(bean, Marker.class, cc);
//        System.out.println(o);
        Marker m = lookupBean(Marker.class);
        Assert.assertNotNull(m);
        System.out.println(m);
    }
    
    @Test
    public void dummy() {
        final BeanManager beanManager = weld.getBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(Object.class);
        for (Bean bean : beans) {
            System.out.println(bean);
        }
    }
    
    protected <T> T lookupBean(Class<? extends T> clazz) {
        final BeanManager beanManager = weld.getBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(clazz);
        Bean bean = beans.iterator().next();
        CreationalContext cc = beanManager.createCreationalContext(bean);
        return (T)beanManager.getReference(bean, clazz, cc);
    }
}
