package com.steeplesoft.plummer.kernel;

import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
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
    private static final Level LOG_LEVEL = Level.FINE;
    private Set<Class<?>> classes;
    private ClassLoader parent;

    public PluginClassLoader(URL[] urls, ClassLoader parent) throws IOException {
        super(urls, parent);
        this.parent = parent;
        classes = new LinkedHashSet<Class<?>>();
        if (urls != null) {
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
                    close(jis);
                }
            }
        }
    }

    private Class<?> defineClass(String className, byte[] byteContent) throws Exception {
        ProtectionDomain pd = getClass().getProtectionDomain();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//                getClass().getClassLoader();

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

            Logger.getLogger(getClass().getName()).log(LOG_LEVEL, "Loading bytecode for {0}", className);
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
        Logger.getLogger(getClass().getName()).log(LOG_LEVEL, "Adding {0}", url.toExternalForm());

        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            Logger.getLogger(getClass().getName()).log(LOG_LEVEL, "***** url = {0}", url.toString());
            method.invoke(Thread.currentThread().getContextClassLoader(), new Object[]{url});
        } catch (Throwable t) {
            throw new RuntimeException("Error when adding url to ClassLoader", t);
        }
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    private byte[] getBytes(JarEntry entry, JarInputStream jis) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[0];

        try {
            byte[] buffer = new byte[2048];
            int read = 0;

            while (jis.available() > 0) {
                read = jis.read(buffer, 0, buffer.length);
                if (read > 0) {
                    baos.write(buffer, 0, read);
                }
            }

            bytes = baos.toByteArray();
        } catch (Exception e) {
        } finally {
            close(baos);
        }
        return bytes;
    }

    private void close(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
