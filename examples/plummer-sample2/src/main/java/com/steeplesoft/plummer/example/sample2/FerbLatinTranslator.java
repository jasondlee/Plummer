/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample2;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Translator;
import javax.inject.Singleton;

/**
 *
 * @author jdlee
 */
@Singleton
@Translator
public class FerbLatinTranslator implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "Ferb Latin";
    }

    @Override
    public String process(String original) {
        StringBuilder sb = new StringBuilder();
        for (String word : original.split(" ")) {
            if (word.length() < 3) {
                sb.append(word).append(" ");
            } else {
                sb.append(word.substring(1))
                    .append(word.substring(0, 1)
                    .toLowerCase()).append("-erb ");
            }
        }
        
        return sb.toString();
    }
    
}
