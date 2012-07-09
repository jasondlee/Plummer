/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Tag;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jdlee
 */
@Tag
public class GoogleMapsProcessor implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "Google Maps Processor";
    }

    @Override
    public String process(String text) {
        Pattern pattern = Pattern.compile("\\[map\\](.*?)\\[\\/map\\]");
        String replaceStr = "<a href=\\\"https://maps.google.com/maps?q=$1\\\">$1</a>";

        Matcher matcher = pattern.matcher(text);
        String result = matcher.replaceAll(replaceStr);
        return result;
    }
}
