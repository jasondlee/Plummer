/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample1;

import org.glassfish.plummer.api.Plugin;
import org.glassfish.plummer.api.ViewFragment;

/**
 *
 * @author jdlee
 */
public class SamplePlugin implements Plugin {
    @ViewFragment(type="foo")
    public static String sample1 = "sample1.xhtml";

    @Override
    public int getPriority() {
        return 500;
    }
}
