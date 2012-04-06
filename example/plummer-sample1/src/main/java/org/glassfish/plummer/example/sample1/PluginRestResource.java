/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample1;

import org.glassfish.plummer.example.model.Marker;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jdlee
 */
//@Path("plugin")
//@ApplicationScoped
public class PluginRestResource implements Marker {

//    @Inject
//    BeanManager beanManager;
//    
////    @Inject
//    MyEjb ejb;
    
    @GET
    public String test() {
//        StringBuilder sb = new StringBuilder(beanManager.hashCode() + "\n");
//        Set<Bean<?>> beans = beanManager.getBeans(Object.class);
//        for (Bean bean : beans) {
//            sb.append(bean).append("\n");
//        }
//        return sb.toString();
        return "";
    }

    @GET
    @Path("ejb")
    public String ejbCall() {
//        return ejb.getValue();
        return "";
    }
}
