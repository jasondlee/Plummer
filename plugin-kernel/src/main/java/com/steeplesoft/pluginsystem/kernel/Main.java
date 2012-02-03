/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.pluginsystem.kernel;

import javax.enterprise.event.Observes;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 *
 * @author jdlee
 */
public class Main {
    public void saySomething(@Observes ContainerInitialized event) {
    }
}
