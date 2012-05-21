package org.glassfish.plummer.kernel;

import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.File;
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
public class PluginClassLoader extends URLClassLoader {

    private static final Logger logger = Logger.getLogger(PluginClassLoader.class.getName());
    private Set<Class<?>> classes;
    ClassLoader parent;

    public PluginClassLoader(URL[] urls, ClassLoader parent) throws IOException {
        super(urls, parent);
        this.parent = parent;
        classes = new LinkedHashSet<Class<?>>();
        if (urls != null) {
            int index = 0;

            for (URL url : urls) {
                JarInputStream jis = null;
                try {
                    addURLToClassLoader(url);

                    jis = new JarInputStream(new BufferedInputStream(url.openStream()));

                    JarEntry entry;
                    while ((entry = jis.getNextJarEntry()) != null) {
                        if (!entry.isDirectory()) {
                            String fileName = entry.getName();
                            if (fileName.endsWith(".class")) {
                                final String className = fileName.substring(0, fileName.length() - 6).
                                        replace("/", ".");
                                final byte[] bytes = getBytes(entry, jis);
                                classes.add(
                                        // Class.forName(className, true, Thread.currentThread().getContextClassLoader())
                                        defineClass(className, bytes));
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, null, ex);
                } finally {
                    closeInputStream(jis);
                }
            }
        }
    }

    private Class<?> defineClass(String className, byte[] byteContent) throws Exception {
        ProtectionDomain pd = getClass().
                getProtectionDomain();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                getClass().
                getClassLoader();

//        classLoader.defineClass(className, byteContent, 0, byteContent.length, pd);
        
        java.lang.reflect.Method jm = null;
        for (java.lang.reflect.Method jm2 :
                ClassLoader.class.getDeclaredMethods()) {
            if (jm2.getName().
                    equals("defineClass")
                    && jm2.getParameterTypes().length == 5) {
                jm = jm2;
                break;
            }
        }
        if (jm == null) {//should never happen, makes findbug happy 
            throw new RuntimeException("cannot find method called defineClass...");
        }
        final java.lang.reflect.Method clM = jm;
        try {
            java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() {

                @Override
                public java.lang.Object run() throws Exception {
                    if (!clM.isAccessible()) {
                        clM.setAccessible(true);
                    }
                    return null;
                }
            });

            Logger.getLogger(getClass().getName()).
                    log(Level.INFO, "Loading bytecode for {0}", className);
            clM.invoke(classLoader, className, byteContent, 0, byteContent.length, pd);
            try {
                return classLoader.loadClass(className);
            } catch (ClassNotFoundException cnfEx) {
                throw new RuntimeException(cnfEx);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

//        return classLoader.loadClass(className);
    }

    private void addURLToClassLoader(URL url) throws IntrospectionException {
        System.out.println("Adding " + url.toExternalForm());

        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            System.out.println("***** url = " + url.toString());
            method.invoke(Thread.currentThread().getContextClassLoader(), new Object[]{url});
        } catch (Throwable t) {
            throw new RuntimeException("Error when adding url to ClassLoader", t);
        }
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    private byte[] getBytes(JarEntry entry, JarInputStream jis) throws IOException {
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

    private void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
