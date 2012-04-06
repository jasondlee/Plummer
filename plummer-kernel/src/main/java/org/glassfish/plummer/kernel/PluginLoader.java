package org.glassfish.plummer.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import org.glassfish.plummer.api.PluginFinder;

public class PluginLoader implements Extension {

    protected static final String SERVICES_NAME = "org.glassfish.plummer.finders";
    private static final Logger logger = Logger.getLogger(PluginLoader.class.getName());
    List<PluginFinder> pluginFinders;

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager beanManager) {
        logger.log(Level.INFO, "\n\n\n\n*******************beforeBeanDiscovery");
        pluginFinders = getPluginFinders();
        for (PluginFinder pluginFinder : pluginFinders) {
            for (Class<?> clazz : pluginFinder.getClasses()) {
                final AnnotatedType<?> annotatedType = beanManager.createAnnotatedType(clazz);
                logger.log(Level.INFO, "Adding AnnotatedType for {0}", annotatedType.toString());
                bbd.addAnnotatedType(annotatedType);
            }
        }
    }

    public void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        logger.log(Level.INFO, "\n\n\n\n*******************afterDeploymentValidation");
        for (PluginFinder pluginFinder : pluginFinders) {
//            pluginFinder.release();
        }
    }

    public static List<PluginFinder> getPluginFinders() {
        List<PluginFinder> finders = new ArrayList<PluginFinder>();
        try {
            Enumeration<URL> e = Thread.currentThread().getContextClassLoader().getResources("META-INF/services/" + SERVICES_NAME);
            while (e.hasMoreElements()) {
                String finderClass = readFileFromUrl(e.nextElement());
                try {
                    Class<?> clazz = Class.forName(finderClass);
                    if (PluginFinder.class.isAssignableFrom(clazz)) {
                        finders.add((PluginFinder) clazz.newInstance());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (finders.isEmpty()) {
            finders.add(new FilesystemPluginFinder());
        }

        return finders;
    }

    protected static String readFileFromUrl(URL url) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder contents = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                contents.append(inputLine);
            }
            in.close();
            return contents.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
