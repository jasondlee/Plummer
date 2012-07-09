/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample1;

import com.steeplesoft.plummer.api.RestResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author jdlee
 */
@Path("chef")
public class PluginRestResource implements RestResource {

    @GET
    public String test(@QueryParam("text") String text) {
        return new ChefTranslator().process(text);
    }
}
