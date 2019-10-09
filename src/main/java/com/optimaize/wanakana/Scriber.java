package com.optimaize.wanakana;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides methods to parse, handle and verify Kana, Kanji and Latin scripts IN REGARDS TO JAPANESE WORDS.
 * <p>
 * The methods here are made for Japanese words specifically, not for other languages, not for sentences.
 * Currently isLatin() means is pure latin, no COMMON characters such as spaces, digits, punctuation.
 * <p>
 * This code is mostly taken directly from the WanaKanaJava class, reformatted, added Javadoc. No tests added yet.
 * <p>
 * Methods exclude punctuations, symbols, spaces etc. unless specified otherwise.
 */
public class Scriber {

    private Scriber() {
    }

    private static final Scriber INSTANCE = new Scriber();

    public static Scriber getInstance() {
        return INSTANCE;
    }

    /**
     * @return Only the Hiragana characters from the string.
     */
    public static String getHiragana(String s) {
        //not good, won't find all
//        return s.replace("[^ぁ-ん]","");

        //this works
        Pattern p = Pattern.compile("\\p{IsHiragana}");
        Matcher matcher = p.matcher(s);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group());
        }
        return result.toString();
    }

    /**
     * @return Only the Katakana characters from the string.
     */
    public static String getKatakana(String s) {
        //not good, won't find all
//        return s.replace("[^゠-ヿ]", "");

        //this works
        Pattern p = Pattern.compile("\\p{IsKatakana}");
        Matcher matcher = p.matcher(s);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group());
        }
        return result.toString();
    }

    /**
     * Checks if a character is from the Katakana script.
     */
    public boolean isCharKatakana(char chr) {
        return isCharInRange(chr, Constants.KATAKANA_START, Constants.KATAKANA_END);
    }

    /**
     * Checks if a character is from the Hiragana script.
     */
    public boolean isCharHiragana(char chr) {
        return isCharInRange(chr, Constants.HIRAGANA_START, Constants.HIRAGANA_END);
    }

    /**
     * Checks if a character is a Japanese punctuation.
     */
    private boolean isCharJapanesePunctuation(char chr) {
        return isCharInRange(chr, Constants.JAPANESE_PUNCTUATION_START, Constants.JAPANESE_PUNCTUATION_END);
    }

    /**
     * Checks if a character is a kana syllable (Hiragana or Katakana).
     */
    public boolean isCharKana(char chr) {
        return isCharHiragana(chr) || isCharKatakana(chr);
    }

    /**
     * Checks if a character is a Japanese Kanji character.
     */
    public boolean isCharKanji(char chr) {
        if (!isCharInRange(chr, Constants.KANJI_START, Constants.KANJI_END)) {
            if (!isCharInRange(chr, Constants.RARE_KANJI_START, Constants.RARE_KANJI_END)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an entire string contains only Hiragana characters.
     */
    public boolean isHiragana(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isCharHiragana(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an entire string contains only Katakana characters.
     */
    public boolean isKatakana(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isCharKatakana(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an entire string contains only Kanji characters.
     */
    public boolean isKanji(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isCharKanji(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an entire string contains only Japanese punctuations.
     */
    public boolean isJapanesePunctuation(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isCharJapanesePunctuation(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string is ASCII.
     */
    public boolean isAscii(String s) {
        return s.matches("\\p{ASCII}+");
    }


//    /**
//     * Normalizes ASCII characters.
//     *
//     * Example: ȩ -> e; ș -> s etc.
//     * @return The normalized string, or an empty string if it's not normalizable.
//     */
//    private String normalizeAscii(String s) {
//        s = Normalizer.normalize(s, Normalizer.Form.NFD);
//        return s.replaceAll("[^\\p{ASCII}]", "");
//    }



    /**
     * Checks if the character is a certain Unicode range.
     * <p>
     * Example: If it's between 0x3040 and 0x309F, then it's a Hiragana character.
     *
     * @param chr   character to verify
     * @param start from which index to start checking
     * @param end   until which index to check
     */
    private boolean isCharInRange(char chr, int start, int end) {
        int unicode = chr;
        return (start <= unicode && unicode <= end);
    }

}
