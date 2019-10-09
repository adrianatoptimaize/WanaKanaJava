package com.optimaize.wanakana;

import crema.math.algo.combinatorics.RuleOfProductComboBuilder;

import java.util.*;

/**
 * <p>Converts Hiragana syllables to Romaji.</p>
 * <p>Convert Katakana to Hiragana before you call this method.</p>
 * <br>
 * <p><strong>Transcription rules for names</strong></p>
 * <br>
 * <p>Syllables ending in 'o', 'i' or 'half-y', followed by 'u', the 'u' is removed.</p>
 * <p>The Syllable ē" transforms into "ee".</p>
 * <br>
 * <p>Long vowels, like "ou" or "oo", are transcribed in latin as "ō", but this character is not used in the Japanese passport. These are the official way of transcribing them:</p>
 * <p>
 * <p>At the end of the word: OO</p>
 * <p>たかとお・たかとう➔TAKATŌ -> TAKATOO</p>
 * <p>Not at the end: O，OH, OO</p>
 *
 * <p>おうの・おおの➔ŌNO -> ONO (or OHNO, OONO)</p>
 * <p>When transcribing, the long vowel "uu", the duplicate "u" is removed.</p>
 *
 * <p><strong>These rules apply for Japenese people who write their names in Romaji when they make their passports.</strong></p>
 */
public class HiraganaToRomajiConverter implements Converter {

    private Scriber scriber = Scriber.getInstance();

    private Map<String, String> hiraToRomaji;

    private HiraganaToRomajiConverter() {
        this.hiraToRomaji = HiraganaToRomajiData.prepareHiraToRomaji();
    }

    private static class ConverterLazyHolder {
        static final HiraganaToRomajiConverter INSTANCE = new HiraganaToRomajiConverter();
    }

    public static HiraganaToRomajiConverter getInstance() {
        return ConverterLazyHolder.INSTANCE;
    }

    /**
     * @return Romaji in which diphthongs are converted into vowels with macrons. See: https://en.wiktionary.org/wiki/macron
     */
    @Override
    public String convert(String hiragana) {
        String chunk = ""; //chars to work with
        int chunkSize; //size of chars to work with
        int cursor = 0; //used to iterate through each character
        int len = hiragana.length();
        int maxChunk = 2; //maximum size of chars to work with
        boolean nextCharIsDoubleConsonant = false;
        StringBuilder latin = new StringBuilder();
        String latinChar = null;

        //Iterate through the entire word, 2 hiragana chars at a time, and then 1 at a time
        while (cursor < len) {
            //can be max 2 characters at once; Math.min in case there is 1 character
            chunkSize = Math.min(maxChunk, len - cursor);
            while (chunkSize > 0) {
                chunk = hiragana.substring(cursor, (cursor + chunkSize)).trim(); //cut from cursor position up until the char to work with

                //we dont do magic, user has to convert it first.
//                //in case there is katakana in the text, by mistake, translate it
//                if (scriber.isKatakana(chunk)) {
//                    chunk = KatakanaToHiraganaConverter.getInstance().convert(chunk);
//                }

                //detect double consonant
                if (chunkSize == 1 & String.valueOf(chunk.charAt(0)).equals("っ") & cursor < (len - 1)) {
                    nextCharIsDoubleConsonant = true;
                    latinChar = "";
                    break;
                }

//                //detect diphthong as last position - NOT NEEDED anymore, I put diphthongs and long vowels in HiraganaToRomajiData
//                if (chunk.contains("う") & hiragana.indexOf("う") == hiragana.length() - 1) {
//                    String s = hiraToRomaji.get(String.valueOf(chunk.charAt(0)));
//                    if (chunk.length() > 1) {
//                        if (s.length() == 1) {
//                            s = s.concat(s);
//                        } else {
//                            s = s.concat(String.valueOf(s.charAt(1)));
//                        }
//                    }
//                    latinChar = s;
//                } else

                //detect diphthong that is not at the end of the string.
                if (chunk.contains("う") & hiragana.indexOf("う") != hiragana.length() - 1 & hiraToRomaji.get(chunk) == null & hiragana.indexOf("う") != 0) {
                    if (latin.toString().endsWith("o")) {
                        latinChar = hiraToRomaji.get(chunk.substring(1, 2));
                        latin.replace(latin.indexOf("o"), latin.indexOf("o") + 1, "ō");
                    } else {
                        latinChar = hiraToRomaji.get(String.valueOf(chunk.charAt(0)));
                    }
                } else {
                    //get the latin translation for the chunk
                    latinChar = hiraToRomaji.get(chunk);
                }

                //add the double consonant if detected
                if ((latinChar != null) && nextCharIsDoubleConsonant) {
                    latinChar = latinChar.charAt(0) + latinChar;
                    nextCharIsDoubleConsonant = false;
                }

                //if chars were translated, preserve chunk size
                if (latinChar != null) {
                    break;
                }

                //if chars were not translated, decrease chunk size
                chunkSize--;
            }

            //if char was not translated at all, add it; it's latin or punctuation
            if (latinChar == null) {
                latinChar = chunk;
            }

            latin.append(latinChar);
            //increase cursor position
            cursor += chunkSize > 0 ? chunkSize : 1;
        }

        String result = latin.toString();
        if (result.endsWith("uu")) {
            result = result.replace("uu", "ū");
        }

        return result;
        //can manually capitalize if you want to. is simple, almost 1 line only.
//        return WordUtils.capitalizeFully(result);
    }

    /**
     * If the Romaji is simple, then this returns 1 entry; the same as you get from {@link #convert}.
     *
     * If the Romaji contains diacritics (macron), then this variants per macron, and DOES NOT KEEP the
     * original Romaji.
     */
    @Override
    public List<String> convertToVariants(String input) {
        String romaji = convert(input);

        RuleOfProductComboBuilder<String> builder = new RuleOfProductComboBuilder<>();

        for (char c : romaji.toCharArray()) {
            builder.newGroup().variants(variantsForChar(c));
        }

        List<List<String>> list = builder.build();

        List<String> result = new ArrayList<>(list.size());
        for (List<String> oneTranscription : list) {
            result.add(String.join("", oneTranscription));
        }
        return result;
    }


    private Iterable<String> variantsForChar(char c) {
        switch (c) {
            case 'ō':
                return Arrays.asList("o", "oh", "oo");
            case 'ū':
                return Collections.singletonList("u");
            case 'ē':
                return Collections.singletonList("ee");
            default:
                return Collections.singletonList("" + c);
        }
    }

}