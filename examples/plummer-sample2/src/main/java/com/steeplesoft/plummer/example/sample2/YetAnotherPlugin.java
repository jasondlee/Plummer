/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.api.Plugin;
import com.steeplesoft.plummer.api.ViewFragment;


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
