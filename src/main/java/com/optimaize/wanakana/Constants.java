package com.optimaize.wanakana;

/**
 * All ranges are INCLUSIVE.
 */
public class Constants {

    /**
     * This differs from the original library.
     * For some reason they start at 0x3041 and miss one character.
     */
    public static final int HIRAGANA_START = 0x3040;
    public static final int HIRAGANA_END = 0x309F;
    public static final int KATAKANA_START = 0x30A0;
    public static final int KATAKANA_END = 0x30FF;
    public static final int KANJI_START = 0x4E00; //common and uncommon kanji
    public static final int KANJI_END = 0x9FAF; //common and uncommon kanji
    public static final int RARE_KANJI_START = 0x3400;
    public static final int RARE_KANJI_END = 0x4DBF;
    public static final int JAPANESE_PUNCTUATION_START = 0x3000;
    public static final int JAPANESE_PUNCTUATION_END = 0x303F;

}
