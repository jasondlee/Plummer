/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.example.model.DataStore;
import javax.inject.Named;

/**
 *
 * @author jdlee
 */
@Named
public class JdbcDataStore implements DataStore {

    @Override
    public String getDescription() {
        return "JDBC-based data store";
    }
    
}
