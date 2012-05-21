/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.model;

import java.io.Serializable;

/**
 *
 * @author jdlee
 */
public interface BlogEntryProcessor extends Serializable {
    String getName();
    String process(String text);
}
