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
    private static List<PluginFinder> pluginFinders;
    List<Class<? extends PluginFinder>> pluginFinderClasses;

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager beanManager) {
        for (PluginFinder pluginFinder : getPluginFinders()) {
            try {
                for (Class<?> clazz : pluginFinder.getClasses()) {
                    final AnnotatedType<?> annotatedType = beanManager.createAnnotatedType(clazz);
                    logger.log(Level.INFO, "Adding AnnotatedType for {0}", annotatedType.toString());
                    bbd.addAnnotatedType(annotatedType);
                }
            } catch (Exception ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
//        for (PluginFinder pluginFinder : pluginFinders) {
//            pluginFinder.release();
//        }
    }
    
    public static synchronized List<PluginFinder> getPluginFinders() {
        if (pluginFinders == null) {
            pluginFinders = new ArrayList<PluginFinder>();
            List<Class<? extends PluginFinder>> list = getPluginExtensions(PluginFinder.class, SERVICES_NAME);
            if (list.isEmpty()) {
                list.add(FilesystemPluginFinder.class);
                //list.add(OsgiPluginFinder.class);
            }
            
            for (Class<? extends PluginFinder> pfc : list) {
                try {
                    pluginFinders.add(pfc.newInstance());
                } catch (Exception ex) {
                    Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return pluginFinders;
    }

    public static <T> List<Class<? extends T>> getPluginExtensions(Class<T> extensionClazz, String fileName) {
        List<Class<? extends T>> extensions = new ArrayList<Class<? extends T>>();
        
        try {
            Enumeration<URL> e = Thread.currentThread().getContextClassLoader().getResources("META-INF/services/" + fileName);
            while (e.hasMoreElements()) {
                String finderClass = readFileFromUrl(e.nextElement());
                try {
                    Class<?> clazz = Class.forName(finderClass);
                    if (PluginFinder.class.isAssignableFrom(clazz)) {
                        extensions.add((Class<? extends T>)clazz);
//                        extensions.add((T) clazz.newInstance());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return extensions;
    }
    
    public static List<PluginFinder> getPluginFinders1() {
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
