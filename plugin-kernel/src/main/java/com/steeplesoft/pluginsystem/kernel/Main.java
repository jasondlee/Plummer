/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.pluginsystem.kernel;

import javax.enterprise.event.Observes;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 *
 * @author jdlee
 */
public class Main {
    public static void main(String... args) {
        Weld weld = new Weld();
        weld.initialize();
        System.out.println("Hi");
        weld.shutdown();
    }
    
    public void saySomething(@Observes ContainerInitialized event) {
    }
}
