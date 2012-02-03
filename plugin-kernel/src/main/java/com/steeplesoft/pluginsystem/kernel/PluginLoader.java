package com.steeplesoft.pluginsystem.kernel;

import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class PluginLoader implements Extension {
    private static final Logger logger = Logger.getLogger(PluginLoader.class.getName());

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager bm) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        Set<URL> jars = new HashSet<URL>();

        for (File jarFile : getFiles()) {
            JarInputStream jis = null;
            try {
                jars.add(jarFile.toURI().toURL());
                jis = new JarInputStream(new BufferedInputStream(new FileInputStream(jarFile)));

                JarEntry entry;

                while ((entry = jis.getNextJarEntry()) != null) {
                    if (!entry.isDirectory()) {
                        String fileName = entry.getName();
                        if (fileName.endsWith(".class")) {
                            classes.add(defineClass(fileName.substring(0, fileName.length() - 6).replace("/", "."), getBytes(entry, jis)));
                        }
                    }
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                closeInputStream(jis);
            }
        }
        
        for (URL url : jars) {
            try {
                addURLToSystemClassLoader(url);
            } catch (IntrospectionException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        for (Class<?> clazz : classes) {
            bbd.addAnnotatedType(bm.createAnnotatedType(clazz));
        }

    }
    
    protected void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    protected File[] getFiles() {
        String pluginDir = System.getProperty("plugin.dir");
        if (pluginDir == null) {
            pluginDir = System.getProperty("user.home") + "/.plugins";
        }
        File[] files = new File(pluginDir).listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().toLowerCase().endsWith(".jar");
            }
        });
        return files;
    }

    protected void addURLToSystemClassLoader(URL url) throws IntrospectionException {
        URLClassLoader systemClassLoader = (URLClassLoader) getClass().getClassLoader();
                //ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> classLoaderClass = URLClassLoader.class;

        try {
            Method method = classLoaderClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(systemClassLoader, new Object[]{url});
        } catch (Throwable t) {
            throw new IntrospectionException("Error when adding url to ClassLoader ");
        }
    }

    protected byte[] getBytes(JarEntry entry, JarInputStream jis) throws IOException {
        long size = entry.getSize();
        byte[] b = new byte[(int) size];
        int rb = 0;
        int chunk;
        while (((int) size - rb) > 0) {
            chunk = jis.read(b, rb, (int) size - rb);
            if (chunk == -1) {
                break;
            }
            rb += chunk;
        }
        return b;
    }

    protected Class<?> defineClass(String className, byte[] byteContent) throws Exception {
        ProtectionDomain pd = getClass().getProtectionDomain();

        java.lang.reflect.Method jm = null;
        for (java.lang.reflect.Method jm2 : ClassLoader.class.getDeclaredMethods()) {
            if (jm2.getName().equals("defineClass") && jm2.getParameterTypes().length == 5) {
                jm = jm2;
                break;
            }
        }
        if (jm == null) {//should never happen, makes findbug happy
            throw new RuntimeException("cannot find method called defineclass...");
        }
        final java.lang.reflect.Method clM = jm;
        try {
            java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction() {

                        @Override
                        public java.lang.Object run() throws Exception {
                            if (!clM.isAccessible()) {
                                clM.setAccessible(true);
                            }
                            return null;
                        }
                    });

            Logger.getLogger(getClass().getName()).log(Level.FINE, "Loading bytecode for {0}", className);
            clM.invoke(getClass().getClassLoader(), className, byteContent, 0,
                    byteContent.length, pd);

            try {
                return getClass().getClassLoader().loadClass(className);
            } catch (ClassNotFoundException cnfEx) {
                throw new RuntimeException(cnfEx);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
