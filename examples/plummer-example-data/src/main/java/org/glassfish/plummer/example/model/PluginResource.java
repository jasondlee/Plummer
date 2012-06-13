/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.glassfish.plummer.api.RestResource;

/**
 *
 * @author jdlee
 */
@Path("plugin")
public class PluginResource implements RestResource {
    @GET
    public String get() {
        return "Text from a plugin";
    }
}
