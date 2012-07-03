/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.kernel.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Application;
import org.glassfish.plummer.api.RestResource;

/**
 *
 * @author jdlee
 */
public class RestApplication extends Application {
    private Set<Class<?>> classes;
    private Logger logger = Logger.getLogger(RestApplication.class.getName());

    @Override
    public Set<Class<?>> getClasses() {
        synchronized (this) {
            if (classes == null) {
                classes = new HashSet<Class<?>>();
                try {
                    InitialContext initialContext = new InitialContext();
                    BeanManager beanManager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
                    Set<Bean<?>> beans = beanManager.getBeans(RestResource.class);
                    for (Bean bean : beans) {
                        final Class beanClass = bean.getBeanClass();
                        logger.log(Level.INFO, "Added type " + beanClass.getName());
                        classes.add(beanClass);
                    }
                } catch (NamingException e) {
                }
            }
        }
        return classes;
    }
}
