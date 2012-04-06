/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp;

import org.glassfish.plummer.example.model.DataStore;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@Model
public class MyBean {
    @Inject
    DataStore dataStore;
    
    public String getExample() {
        return dataStore.getDescription();
    }
}
