/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample1;

import com.steeplesoft.plummer.api.Plugin;
import com.steeplesoft.plummer.api.ViewFragment;

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
