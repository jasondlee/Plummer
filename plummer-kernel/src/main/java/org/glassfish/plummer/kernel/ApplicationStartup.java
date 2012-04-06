/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.kernel;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author jdlee
 */
@WebListener
public class ApplicationStartup implements ServletContainerInitializer, ServletContextListener {
    private static final Logger logger = Logger.getLogger(ApplicationStartup.class.getName());
    
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        logger.log(Level.INFO, "\n\n\n\n\n\n********************onStartup()");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.INFO, "\n\n\n\n\n\n********************contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
}
