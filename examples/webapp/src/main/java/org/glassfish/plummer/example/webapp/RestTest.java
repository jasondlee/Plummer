/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.glassfish.plummer.api.RestResource;

/**
 *
 * @author jdlee
 */
@Path("test")
public class RestTest implements RestResource {
    @GET
    public String test() {
        return "RestResource successfully deployed!";
    }
}
