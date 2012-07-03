/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import org.glassfish.plummer.example.model.BlogPostedEvent;

/**
 *
 * @author jdlee
 */
public class PostProcessing {
    private Logger logger = Logger.getLogger(PostProcessing.class.getName());

    public void sendEmail(@Observes BlogPostedEvent event) {
        logger.log(Level.INFO, "Email send simulated!");
    }
}
