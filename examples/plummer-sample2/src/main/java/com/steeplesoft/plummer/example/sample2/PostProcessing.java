/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.example.model.BlogPostedEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;

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
