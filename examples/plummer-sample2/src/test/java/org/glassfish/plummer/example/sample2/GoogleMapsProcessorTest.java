/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

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
        String text = p.process("The address for Disneyland is [map]1313 S. Disneyland Drive, Anaheim, CA 92802[/map]. " +
                "The address for JAXConf is [map]1675 Owens Street, San Francisco, CA 94142[/map].");
        Assert.assertEquals("The address for Disneyland is <a href=\"https://maps.google.com/maps?q=\"1313 S. Disneyland Drive, Anaheim, CA 92802\">1313 S. Disneyland Drive, Anaheim, CA 92802</a>. The address for JAXConf is <a href=\"https://maps.google.com/maps?q=\"1675 Owens Street, San Francisco, CA 94142\">1675 Owens Street, San Francisco, CA 94142</a>.", text);
    }
}
