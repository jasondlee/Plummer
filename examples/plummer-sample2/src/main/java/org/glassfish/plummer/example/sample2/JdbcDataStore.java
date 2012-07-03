/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

import javax.inject.Named;
import org.glassfish.plummer.example.model.DataStore;

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
