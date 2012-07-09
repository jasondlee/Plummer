/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.webapp.translator;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Translator;
import javax.inject.Singleton;

/**
 *
 * @author jdlee
 */
@Singleton
@Translator
public class FooTranslator implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "foo";
    }

    @Override
    public String process(String original) {
        return "foo " + original;
    }
    
}
