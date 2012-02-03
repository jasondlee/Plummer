/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.pluginsystem.finder.fs;

import com.steeplesoft.pluginsystem.data.PluginFinder;
import java.util.Set;

/**
 *
 * @author jdlee
 */
public class FilesystemPluginFinder implements PluginFinder {

    @Override
    public Set<Class<?>> getClasses() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void release() {
        // No op
    }
    
}
