/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@ManagedBean(name="jsfBean")
@RequestScoped
public class JsfBean {
    @Inject
    private BeanManager beanManager;
    
    public List<String> getSomeData() {
        List<String> list = new ArrayList<String>();
        Set<Bean<?>> beans = beanManager.getBeans(Object.class);
        for (Bean bean : beans) {
            list.add(bean.toString());
        }
        return list;
    }
}
