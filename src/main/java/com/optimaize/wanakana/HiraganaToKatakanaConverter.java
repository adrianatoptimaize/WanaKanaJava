package com.optimaize.wanakana;

/**
 * Converts Hiragana syllables to Katakana.
 * The conversion is 1:1.
 */
public class HiraganaToKatakanaConverter implements Converter {

    private HiraganaToKatakanaConverter() {
    }

    private static final HiraganaToKatakanaConverter INSTANCE = new HiraganaToKatakanaConverter();

    public static HiraganaToKatakanaConverter getInstance() {
        return INSTANCE;
    }

    /**
     * Converts Hiragana script to Katakana script
     */
    @Override
    public String convert(String hiragana) {
        int unicode;
        StringBuilder katakana = new StringBuilder();

        for (int i = 0; i < hiragana.length(); i++) {
            char hiraganaChar = hiragana.charAt(i);

            if (Scriber.getInstance().isCharHiragana(hiraganaChar)) {
                unicode = hiraganaChar;
                unicode += Constants.KATAKANA_START - Constants.HIRAGANA_START;
                katakana.append(String.valueOf(Character.toChars(unicode)));
            } else {
                katakana.append(hiraganaChar);
            }
        }

        return katakana.toString();
    }

}
