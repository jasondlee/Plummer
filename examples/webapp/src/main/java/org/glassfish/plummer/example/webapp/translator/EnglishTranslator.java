/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp.translator;

import javax.inject.Singleton;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.Translator;
import org.glassfish.plummer.example.webapp.English;

/**
 *
 * @author jdlee
 */
@English
@Singleton
@Translator
public class EnglishTranslator implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "English";
    }

    @Override
    public String process(String original) {
        return original;
    }
    
}
