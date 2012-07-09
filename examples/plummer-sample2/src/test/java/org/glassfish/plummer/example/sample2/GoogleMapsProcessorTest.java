/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.example.sample2.GoogleMapsProcessor;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jdlee
 */
public class GoogleMapsProcessorTest {
    @Test
    public void testTag() {
        GoogleMapsProcessor p = new GoogleMapsProcessor();
        String text = p.process("The address for Disneyland is [map]1313 S. Disneyland Drive, Anaheim, CA 92802[/map]. The address for JAXConf is [map]1675 Owens Street, San Francisco, CA 94142[/map].");
        Assert.assertFalse(text.contains("[map]"));
        Assert.assertFalse(text.contains("[/map]"));
        Assert.assertTrue(text.contains("92802\">1313 S."));
        Assert.assertTrue(text.contains("92802</a>. The"));
        Assert.assertTrue(text.contains("is <a href="));
    }
}
