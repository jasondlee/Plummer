/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.api;

import java.util.Set;

/**
 *
 * @author jdlee
 */
public interface PluginFinder {
    Set<Class<?>> getClasses();
    void release();
}
