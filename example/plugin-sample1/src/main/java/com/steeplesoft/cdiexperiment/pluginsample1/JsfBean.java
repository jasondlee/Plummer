/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.cdiexperiment.pluginsample1;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;



/**
 *
 * @author jdlee
 */
@ManagedBean(name="jsfBean")
@RequestScoped
public class JsfBean {
    public String getSomeData() {
        return "jsf managed bean";
    }
}
