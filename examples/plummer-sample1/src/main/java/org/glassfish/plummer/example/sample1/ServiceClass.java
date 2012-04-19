package org.glassfish.plummer.example.sample1;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
public class ServiceClass {
    @Inject
    BeanManager beanManager;
    
    public String getValue() {
        return "ejb value";
    }
}
