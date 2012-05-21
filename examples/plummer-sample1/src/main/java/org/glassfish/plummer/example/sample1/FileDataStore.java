/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample1;

import org.glassfish.plummer.example.model.DataStore;

/**
 *
 * @author jdlee
 */
public class FileDataStore implements DataStore {
//    @Inject
    private ServiceClass sc;

    @Override
    public String getDescription() {
        return "File-based data store"; // + sc.toString();
    }
    
}
