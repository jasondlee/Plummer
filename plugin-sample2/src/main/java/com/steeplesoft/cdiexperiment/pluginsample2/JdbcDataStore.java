/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.cdiexperiment.pluginsample2;

import com.steeplesoft.cdiexperiment.plugin.model.DataStore;
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
