/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.sample1;

import java.util.StringTokenizer;
import javax.inject.Singleton;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.Translator;

/**
 *
 * @author jdlee
 */
@Singleton
@Translator
public class ChefTranslator implements BlogEntryProcessor {

    @Override
    public String getName() {
        return "Chef";
    }

    @Override
    public String process(String original) {
        return encheferizeLine(original);
    }

    /**
     * [The following methods] translate English to mock-Swedish a-la the Swedish Chef
     * <br>The translation rules were taken as faithfully as possible from Lex
     * source at <A HREF="ftp://shell5.ba.best.com/pub/tbrowne/swedish_chef/">
     * ftp://shell5.ba.best.com/pub/tbrowne/swedish_chef/</A> Please report any
     * inconsistencies to <A HREF="mailto:wtpooh@leland.stanford.edu">
     * wtpooh@leland.stanford.edu</a>
     *
     * @version 0.1 1/9/98
     * @author Josh Vura-Weis	wtpooh@leland.stanford.edu
     */
    /*
     * Please feel free to suggest improvements, both in efficiency and style
     * (It needs both - I whipped it together pretty quickly)
     */
    public static String encheferizeLine(String line) {
        StringBuilder sb = new StringBuilder();
        String word = "", delimiters = " \n\t\\,<.>/?;:'\"[{]}|=+-_!@#$%^&*()~`";
        StringTokenizer st;

        st = new StringTokenizer(line, delimiters, true);
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            if (delimiters.indexOf(word) == -1) { //not delimiter   
                sb.append(encheferizeWord(word));
            } else {
                sb.append(word); //Spit back delimiter
            }
        }
        if (word.equals(".")) {
            sb.append("\n");
            sb.append("Bork Bork Bork!");
        }

        return (sb.toString());
    }

    private static String encheferizeWord(String word) {
        if (word.toLowerCase().equals("bork")) {
            return word;
        }

        char letter;

        int count = 0;
        int len = word.length();
        StringBuilder buff = new StringBuilder();
        boolean i_seen = false;

        while (count < len) {
            boolean isLast = count == (len - 1);

            letter = word.charAt(count);

            if (count == 0) { //Beginning-of-word rules
                if (letter == 'e') {
                    buff.append("i");
                    count++;
                    continue;
                } else if (letter == 'E') {
                    buff.append("I");
                    count++;
                    continue;
                } else if (letter == 'o') {
                    buff.append("oo");
                    count++;
                    continue;
                } else if (letter == 'O') {
                    buff.append("Oo");
                    count++;
                    continue;
                }
            } else {  //End of Beginning-of-word rules, Start of In-Word rules
                if (letter == 'e') {
                    if (!isLast && word.charAt(count + 1) == 'w') {
                        buff.append("oo");
                        count += 2;
                        continue;
                    } else if (isLast) {
                        buff.append("e-a");
                        count++;
                        continue;
                    }
                } else if (letter == 'f') {
                    buff.append("ff");
                    count++;
                    continue;
                } else if (letter == 'i') {
                    if (!isLast && word.charAt(count + 1) == 'r') {
                        buff.append("ur");
                        count += 2;
                        continue;
                    } else if (!i_seen) {
                        buff.append("ee");
                        count++;
                        i_seen = true;
                        continue;
                    }
                } else if (letter == 'o') {
                    if (!isLast && word.charAt(count + 1) == 'w') {
                        buff.append("oo");
                        count += 2;
                        continue;
                    } else {
                        buff.append("u");
                        count++;
                        continue;
                    }
                } else if (count <= len - 4 && letter == 't' && word.charAt(count + 1) == 'i'
                        && word.charAt(count + 2) == 'o' && word.charAt(count + 3) == 'n') {
                    buff.append("shun");
                    count += 4;
                    continue;
                } else if (letter == 'u') {
                    buff.append("oo");
                    count++;
                    continue;
                } else if (letter == 'U') {
                    buff.append("Oo");
                    count++;
                    continue;
                }
            }  //end if In-word rules
            //End if Word-placement rules, Start of Anywhere rules
            if (letter == 'A') {
                if (!isLast && word.charAt(count + 1) == 'n') {
                    buff.append("Un");
                    count = count + 2;
                    continue;
                } else if (!isLast && word.charAt(count + 1) == 'u') {
                    buff.append("Oo");
                    count = count + 2;
                    continue;
                } else if (!isLast) {
                    buff.append("E");
                    count++;
                    continue;
                }
            } else if (letter == 'a') {
                if (!isLast && word.charAt(count + 1) == 'n') {
                    buff.append("un");
                    count = count + 2;
                    continue;
                } else if (!isLast && word.charAt(count + 1) == 'u') {
                    buff.append("oo");
                    count = count + 2;
                    continue;
                } else if (!isLast) {
                    buff.append("e");
                    count++;
                    continue;
                }
            } else if (letter == 'e') {
                if (!isLast && word.charAt(count + 1) == 'n'
                        && count == len - 2) {
                    buff.append("ee");
                    count += 2;
                    continue;
                } else if (count > 0) {
                }
            } else if (letter == 't') {
                if (count == len - 2 && word.charAt(count + 1) == 'h') {
                    buff.append("t");
                    count += 2;
                    continue;
                } else if (count <= len - 3 && word.charAt(count + 1) == 'h'
                        && word.charAt(count + 2) == 'e') {
                    buff.append("zee");
                    count += 3;
                    continue;
                }
            } else if (letter == 'T' && count <= len - 3 && word.charAt(count + 1) == 'h'
                    && word.charAt(count + 2) == 'e') {
                buff.append("Zee");
                count += 3;
                continue;
            } else if (letter == 'v') {
                buff.append("f");
                count++;
                continue;
            } else if (letter == 'V') {
                buff.append("F");
                count++;
                continue;
            } else if (letter == 'w') {
                buff.append("v");
                count++;
                continue;
            } else if (letter == 'W') {
                buff.append("V");
                count++;
                continue;
            }
            //End of rules.  Whatever is left stays itself

            buff.append(letter);
            count++;
        }

        return (buff.toString());
    }
}
