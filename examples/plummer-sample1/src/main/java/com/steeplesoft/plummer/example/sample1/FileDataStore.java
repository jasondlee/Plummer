/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample1;

import com.steeplesoft.plummer.example.model.DataStore;


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
