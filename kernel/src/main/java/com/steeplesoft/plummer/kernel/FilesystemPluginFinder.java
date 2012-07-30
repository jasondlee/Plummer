/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.kernel;

import com.steeplesoft.plummer.api.PluginFinder;
import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jdlee
 */
public class FilesystemPluginFinder implements PluginFinder {

    private static final Logger logger = Logger.getLogger(FilesystemPluginFinder.class.getName());
    private static Set<Class<?>> classes;
    private static PluginClassLoader pcl;

    @Override
    public synchronized Set<Class<?>> getClasses() {
        if (pcl == null) {
            final File[] jars = getFiles();
            if (jars != null) {
                try {
                    URL[] urls = new URL[jars.length];
                    int index = 0;
                    for (File jar : jars) {
                        urls[index++] = jar.toURI().toURL();
                    }

                    pcl = new PluginClassLoader(urls, Thread.currentThread().getContextClassLoader());
                    // populate classes
                } catch (IOException ex) {
                    Logger.getLogger(FilesystemPluginFinder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            if (!Thread.currentThread().getContextClassLoader().equals(pcl)) {
//                Thread.currentThread().setContextClassLoader(pcl);
//            }
        }
        return pcl.getClasses();

//        if (classes == null) {
//            loadClasesFromJars();
//        }
//        return classes;
    }

    @Override
    public void release() {
        classes.clear();
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

    protected void loadClasesFromJars() {
        classes = new LinkedHashSet<Class<?>>();
        final File[] files = getFiles();
        if (files != null) {
            URL[] urls = new URL[files.length];
            int index = 0;

            for (File jarFile : files) {
                JarInputStream jis = null;
                try {
                    final URL url = jarFile.toURI().toURL();
                    urls[index++] = url;
                    try {
                        addURLToClassLoader(url);
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }

                    //jis = new JarInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
                    jis = new JarInputStream(new BufferedInputStream(url.openStream()));

                    JarEntry entry;

                    while ((entry = jis.getNextJarEntry()) != null) {
                        if (!entry.isDirectory()) {
                            String fileName = entry.getName();
                            if (fileName.endsWith(".class")) {
                                final String className = fileName.substring(0, fileName.length() - 6).
                                        replace("/", ".");
                                classes.add(
                                        //                                    Class.forName(className, true, Thread.currentThread().getContextClassLoader())
                                        defineClass(className, getBytes(entry, jis)));
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, null, ex);
                } finally {
                    closeInputStream(jis);
                }
            }

            //new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
//                for (URL url : urls) {
//                    try {
//                        addURLToClassLoader(url);
//                    } catch (IntrospectionException ex) {
//                        logger.log(Level.SEVERE, null, ex);
//                    }
//                }
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

    protected void addURLToClassLoader(URL url) throws IntrospectionException {
        System.out.println("Adding " + url.toExternalForm());

        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//                    getClass().getClassLoader();

            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            System.out.println(url.toString());
            method.invoke(classLoader, new Object[]{url});
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
        final ClassLoader classLoader = //Thread.currentThread().getContextClassLoader();
                getClass().getClassLoader();

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

            Logger.getLogger(getClass().getName()).log(Level.INFO, "Loading bytecode for {0}", className);
            clM.invoke(classLoader, className, byteContent, 0,
                       byteContent.length, pd);

            try {
                return classLoader.loadClass(className);
            } catch (ClassNotFoundException cnfEx) {
                throw new RuntimeException(cnfEx);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
