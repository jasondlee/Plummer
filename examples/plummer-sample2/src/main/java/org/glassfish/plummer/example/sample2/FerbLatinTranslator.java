/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample2;

import javax.inject.Singleton;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.Translator;

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
