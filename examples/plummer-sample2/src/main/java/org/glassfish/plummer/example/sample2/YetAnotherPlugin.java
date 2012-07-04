/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

import org.glassfish.plummer.api.Plugin;
import org.glassfish.plummer.api.ViewFragment;

/**
 *
 * @author jdlee
 */
public class YetAnotherPlugin implements Plugin {
    @ViewFragment(type="foo")
    public static final String fragment = "anotherFragment.xhtml";

    @Override
    public int getPriority() {
        return 250;
    }

}
