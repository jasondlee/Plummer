/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.pluginsystem.data;

import java.util.Set;

/**
 *
 * @author jdlee
 */
public interface PluginFinder {
    Set<Class<?>> getClasses();
    void release();
}
