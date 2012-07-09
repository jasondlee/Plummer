/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.model;

import com.steeplesoft.plummer.api.RestResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
