package com.optimaize.wanakana;

/**
 * Converts Katakana syllables to Hiragana.
 * The conversion is 1:1.
 */
public class KatakanaToHiraganaConverter implements Converter {

    private KatakanaToHiraganaConverter() {
    }

    private static final KatakanaToHiraganaConverter INSTANCE = new KatakanaToHiraganaConverter();

    public static KatakanaToHiraganaConverter getInstance() {
        return INSTANCE;
    }


    /**
     * Coverts Katakana script to Hiragana script
     */
    @Override
    public String convert(String kata) {
        int unicode;
        StringBuilder hiragana = new StringBuilder();

        for (int i = 0; i < kata.length(); i++) {
            char katakanaChar = kata.charAt(i);

            if (Scriber.getInstance().isCharKatakana(katakanaChar)) {
                unicode = katakanaChar;
                unicode += Constants.HIRAGANA_START - Constants.KATAKANA_START;
                hiragana.append(String.valueOf(Character.toChars(unicode)));
            } else {
                hiragana.append(katakanaChar);
            }
        }

        return hiragana.toString();
    }

}
