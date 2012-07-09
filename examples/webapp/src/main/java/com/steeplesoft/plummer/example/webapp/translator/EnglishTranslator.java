/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.webapp.translator;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Translator;
import com.steeplesoft.plummer.example.webapp.English;
import javax.inject.Singleton;

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
