/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.webapp;

import com.steeplesoft.plummer.api.RestResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
