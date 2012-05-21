/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp.translator;

import javax.inject.Singleton;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.Translator;

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
