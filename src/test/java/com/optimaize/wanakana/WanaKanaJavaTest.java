package com.optimaize.wanakana;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * See: https://github.com/WaniKani/WanaKana/blob/master/test/core/toRomaji.test.js
 */
public class WanaKanaJavaTest {

    private HiraganaToRomajiConverter hiraganaToRomajiConverter = HiraganaToRomajiConverter.getInstance();
    private KatakanaToHiraganaConverter katakanaToHiraganaConverter = KatakanaToHiraganaConverter.getInstance();


    @Test(dataProvider = "hiraganaToRomaji")
    public void hiraganaToRomaji(String input, String expected) {
        String romaji = hiraganaToRomajiConverter.convert(input);

        assertEquals(romaji, expected);
    }
    @DataProvider
    public Object[][] hiraganaToRomaji() {
        return new Object[][]{
                {"わにかに　が　すごい　だ", "wanikani ga sugoi da"},
                {"きんにくまん", "kinnikuman"},
                {"んんにんにんにゃんやん", "nnninninnyanyan"},

                //https://github.com/WaniKani/WanaKana/blob/master/test/core/toRomaji.test.js javascript library writes chatcha isntead of chaccha
                //Andreea says our variant is better.
                {"かっぱ　たった　しゅっしゅ　ちゃっちゃ　やっつ", "kappa tatta shusshu chaccha yattsu"}
        };
    }

    @Test(dataProvider = "katakanaToRomaji")
    public void katakanaToRomaji(String input, String expected) {
        String katakana = katakanaToHiraganaConverter.convert(input);
        String romaji = hiraganaToRomajiConverter.convert(katakana);

        assertEquals(romaji, expected);
    }
    @DataProvider
    public Object[][] katakanaToRomaji() {
        return new Object[][]{
                {"ワニカニ　ガ　スゴイ　ダ", "wanikani ga sugoi da"}
        };
    }

    @Test(dataProvider = "mixedKanaToRomaji")
    public void mixedKanaToRomaji(String input, String expected) {
        String katakana = katakanaToHiraganaConverter.convert(input);
        String romaji = hiraganaToRomajiConverter.convert(katakana);

        assertEquals(romaji, expected);
    }
    @DataProvider
    public Object[][] mixedKanaToRomaji() {
        return new Object[][]{
                {"ワニカニ　が　すごい　だ", "wanikani ga sugoi da"}
        };
    }


}