/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.webapp;

import com.steeplesoft.plummer.example.model.DataStore;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@Model
public class MyBean {
    @Inject
    Instance<DataStore> dataStores;
    
    public String getExample() {
        StringBuilder sb = new StringBuilder();
        for (DataStore ds : dataStores) {
            sb.append(ds.getDescription()).append("<p/>");
        }
        return sb.toString();
    }
}
