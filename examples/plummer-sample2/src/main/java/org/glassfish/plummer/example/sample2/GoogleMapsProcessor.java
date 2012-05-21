/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.plummer.example.model.BlogEntryProcessor;

/**
 *
 * @author jdlee
 */
public class GoogleMapsProcessor implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "Google Maps Processor";
    }

    @Override
    public String process(String text) {
        Pattern p = Pattern.compile("(\\d+? .*, .*, .* \\d{5}?)");
        Matcher m = p.matcher(text);
        
        System.out.println(m.groupCount());
        
        return text;
    }
    
    public static void main (String... args) {
        GoogleMapsProcessor p = new GoogleMapsProcessor();
        
        System.out.println(p.process("14613 N. May Ave., Oklahoma City, OK 73134"));
    }
}
