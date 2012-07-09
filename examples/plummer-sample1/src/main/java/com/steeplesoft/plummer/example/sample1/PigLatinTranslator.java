/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.sample1;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Translator;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Singleton;

/**
 *
 * @author jdlee
 */
@Singleton
@Translator
public class PigLatinTranslator implements BlogEntryProcessor {
    String[] vowelArray = {"a", "e", "i", "o", "u", "A", "E", "I", "O", "U"};
    Set<String> vowels = new TreeSet<String>(Arrays.asList(vowelArray));
    Set<String> specialChars = new TreeSet<String>(Arrays.asList(new String[] 
        {"#", ".", ",", "!", "(", ")", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));

    @Override
    public String getName() {
        return "Pig Latin";
    }

    @Override
    public String process(String original) {
        return pigLatin(original);
    }
    
    protected String pigLatin(String original) {
        StringBuilder sb = new StringBuilder();
        
        for (String token : original.split(" ")) {
            sb.append(pigWord(token))
                    .append(" ");
        }
        return sb.toString();
    }
    
    protected String pigWord(String word) {
        if (specialChars.contains(word.substring(0,1))) {
            return word;
        }
        else if (beginsWithVowel(word)) {
            return word + "way";
        } else {
            // TODO: look for punctuation before modifiying ending
            int index = findFirstNonVowel(word);
            
            return word.substring(index) + word.substring(0, index).toLowerCase() + "ay";
        }
    }
    
    protected boolean beginsWithVowel(String word) {
        return vowels.contains(word.substring(0,1));
        /*
        for (int index = 0; index < vowelArray.length; index++) {
            if (word.startsWith(vowelArray[index])) {
                return true;
            }
        }
        
        return false;
        */
    }
    
    protected int findFirstNonVowel(String word) {
        int index = 1;
        for (int i = 1; i < word.length(); i++) {
            if (vowels.contains(word.substring(index,1))) {
                return index;
            }
        }
        
        return index;
    }

    /**
     * Method to translate a sentence word by word.
     *
     * @param s The sentence in English
     * @return The pig latin version
     */
    private String pigLatin1(String s) {
        String latin = "";
        int i = 0;
        while (i < s.length()) {

            // Take care of punctuation and spaces
            while (i < s.length() && !isLetter(s.charAt(i))) {
                latin = latin + s.charAt(i);
                i++;
            }

            // If there aren't any words left, stop.
            if (i >= s.length()) {
                break;
            }

            // Otherwise we're at the beginning of a word.
            int begin = i;
            while (i < s.length() && isLetter(s.charAt(i))) {
                i++;
            }

            // Now we're at the end of a word, so translate it.
            int end = i;
            latin = latin + pigWord(s.substring(begin, end));
        }
        return latin;
    }

    /**
     * Method to test whether a character is a letter or not.
     *
     * @param c The character to test
     * @return True if it's a letter
     */
    private boolean isLetter(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    /**
     * Method to translate one word into pig latin.
     *
     * @param word The word in english
     * @return The pig latin version
     */
    private String pigWord1(String word) {
        int split = firstVowel(word);
        return word.substring(split) + word.substring(0, split).toLowerCase() + "ay";
    }

    /**
     * Method to find the index of the first vowel in a word.
     *
     * @param word The word to search
     * @return The index of the first vowel
     */
    private int firstVowel(String word) {
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'a' || word.charAt(i) == 'e'
                    || word.charAt(i) == 'i' || word.charAt(i) == 'o'
                    || word.charAt(i) == 'u') {
                return i;
            }
        }
        return 0;
    }
}
