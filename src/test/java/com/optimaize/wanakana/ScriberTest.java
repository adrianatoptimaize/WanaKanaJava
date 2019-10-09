package com.optimaize.wanakana;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ScriberTest {

    private static final Scriber scriber = Scriber.getInstance();

    @Test(dataProvider = "getHiragana")
    public void getHiragana(String input, String expected) {
        String result = Scriber.getHiragana(input);

        assertEquals(result, expected);
    }
    @DataProvider
    public Object[][] getHiragana() {
        return new Object[][] {
                //simple cases
                {"すずき","すずき"},
                {"すずエき","すずき"},
                {"す日エき","すき"},
                {"すず日き","すずき"},
                {"日すずき","すずき"},
                {"すずRき","すずき"},
                {"すλエき","すき"},
                {"すкすき","すすき"},

                //only take Hira, ignore rest
                {"すす.き","すすき"},
                {"すす\u3000き","すすき"},
                {"すす【き","すすき"},

                //nothing remains
                {"",""},
                {"ツモト",""},
                {"Romaji",""},
        };
    }

    @Test(dataProvider = "getKatakana")
    public void getKatakana(String input, String expected) {
        String result = Scriber.getKatakana(input);

        assertEquals(result, expected);
    }
    @DataProvider
    public Object[][] getKatakana() {
        return new Object[][] {
                {"",""},
                {"すずき",""},
                {"Romaji",""},
                {"ツモト","ツモト"},
                {"ツXモト","ツモト"},
                {"ツ日モト","ツモト"},
                {"日ツモト","ツモト"},
                {"ツモト日","ツモト"},
                {"ツλモト","ツモト"},
                {"ツモкト","ツモト"},

                {"ツモ.ト","ツモト"},
                {"ツモ\u3000ト","ツモト"},
                {"ツモ【ト","ツモト"},
        };
    }

    @Test
    public void isCharHiragana() {
        assertTrue(scriber.isCharHiragana('し'));

        assertFalse(scriber.isCharHiragana('日'));
        assertFalse(scriber.isCharHiragana('エ'));
        assertFalse(scriber.isCharHiragana('R'));
        assertFalse(scriber.isCharHiragana('λ'));
        assertFalse(scriber.isCharHiragana('к'));

        assertFalse(scriber.isCharHiragana('.'));//punctuations
        assertFalse(scriber.isCharHiragana(' '));//punctuations
        assertFalse(scriber.isCharHiragana('\u3000'));//ideographic space
        assertFalse(scriber.isCharHiragana('【'));//ideographic space
    }

    @Test
    public void isCharKatakana() {
        assertTrue(scriber.isCharKatakana('エ'));

        assertFalse(scriber.isCharKatakana('日'));
        assertFalse(scriber.isCharKatakana('し'));
        assertFalse(scriber.isCharKatakana('R'));
        assertFalse(scriber.isCharKatakana('λ'));
        assertFalse(scriber.isCharKatakana('к'));

        assertFalse(scriber.isCharKatakana('.'));//punctuations
        assertFalse(scriber.isCharKatakana(' '));//punctuations
        assertFalse(scriber.isCharKatakana('\u3000'));//ideographic space
        assertFalse(scriber.isCharKatakana('【'));//punctuations
    }

    @Test
    public void isCharKana() {
        assertTrue(scriber.isCharKana('し'));
        assertTrue(scriber.isCharKana('エ'));

        assertFalse(scriber.isCharKana('日'));
        assertFalse(scriber.isCharKana('R'));
        assertFalse(scriber.isCharKana('λ'));
        assertFalse(scriber.isCharKana('к'));

        assertFalse(scriber.isCharKana('\u3000'));//ideographic space
        assertFalse(scriber.isCharKana(' '));//punctuations
        assertFalse(scriber.isCharKana('.'));//punctuations
        assertFalse(scriber.isCharKana('【'));//punctuations
        assertFalse(scriber.isCharKana('】'));//punctuations
        assertFalse(scriber.isCharKana('。'));//punctuations
    }

    @Test
    public void isCharKanji() {
        assertTrue(scriber.isCharKanji('日'));

        assertFalse(scriber.isCharKanji('R'));
        assertFalse(scriber.isCharKanji('し'));
        assertFalse(scriber.isCharKanji('エ'));
        assertFalse(scriber.isCharKanji('λ'));
        assertFalse(scriber.isCharKanji('к'));

        assertFalse(scriber.isCharKanji('\u3000'));//ideographic space
        assertFalse(scriber.isCharKanji('【'));//punctuations
        assertFalse(scriber.isCharKanji('】'));//punctuations
        assertFalse(scriber.isCharKanji('。'));//punctuations
        assertFalse(scriber.isCharKanji(' '));//punctuations
        assertFalse(scriber.isCharKanji('.'));//punctuations
    }

    @Test
    public void isHiragana() {
        assertTrue(scriber.isHiragana("しょう"));

        assertFalse(scriber.isHiragana("Romaji"));
        assertFalse(scriber.isHiragana("日本"));
        assertFalse(scriber.isHiragana("エーイチ"));
        assertFalse(scriber.isHiragana("Ελλάς"));
        assertFalse(scriber.isHiragana("Mосква"));

        assertFalse(scriber.isHiragana("しょ う")); //normal space
        assertFalse(scriber.isHiragana("しょう\u3000")); //ideographic space
        assertFalse(scriber.isHiragana("しょう.")); //with punctuations
        assertFalse(scriber.isHiragana("【　】")); //with punctuations
    }

    @Test
    public void isKatakana() {
        assertTrue(scriber.isKatakana("エーイチ"));

        assertFalse(scriber.isKatakana("Romaji"));
        assertFalse(scriber.isKatakana("日本"));
        assertFalse(scriber.isKatakana("しょう"));
        assertFalse(scriber.isKatakana("Ελλάς"));
        assertFalse(scriber.isKatakana("Mосква"));

        assertFalse(scriber.isKatakana("エーイ チ")); //normal space
        assertFalse(scriber.isKatakana("エーイチ\u3000")); //ideographic space
        assertFalse(scriber.isKatakana("エーイチ.")); //with punctuations
        assertFalse(scriber.isKatakana("【　】")); //with punctuations
    }

    @Test
    public void isKanji() {
        assertTrue(scriber.isKanji("日本"));

        assertFalse(scriber.isKanji("Romaji"));
        assertFalse(scriber.isKanji("しょう"));
        assertFalse(scriber.isKanji("エーイチ"));
        assertFalse(scriber.isKanji("Ελλάς"));
        assertFalse(scriber.isKanji("Mосква"));

        assertFalse(scriber.isKanji("日 本")); //normal space
        assertFalse(scriber.isKanji("日本\u3000")); //ideographic space
        assertFalse(scriber.isKanji("日本.")); //with punctuations
        assertFalse(scriber.isKanji("【　】")); //with punctuations
    }

    @Test
    public void isJapanesePunctuation() {
        assertTrue(scriber.isJapanesePunctuation("\u3000")); //ideographic space
        assertTrue(scriber.isJapanesePunctuation("【　】"));
        assertTrue(scriber.isJapanesePunctuation("\u3001")); //ideographic comma

        assertFalse(scriber.isJapanesePunctuation("日本"));
        assertFalse(scriber.isJapanesePunctuation("エーイチ"));
        assertFalse(scriber.isJapanesePunctuation("Rom aji"));
        assertFalse(scriber.isJapanesePunctuation("Ελλάς!"));
        assertFalse(scriber.isJapanesePunctuation("Mосква"));
        assertFalse(scriber.isJapanesePunctuation("Romaji"));

        assertFalse(scriber.isJapanesePunctuation("."));
        assertFalse(scriber.isJapanesePunctuation(" "));
        assertFalse(scriber.isJapanesePunctuation("-"));
    }

    @Test
    public void isAscii() {
        assertTrue(scriber.isAscii("Romaji"));
        assertTrue(scriber.isAscii("Roma ji"));
        assertTrue(scriber.isAscii("Romaji!"));
        assertTrue(scriber.isAscii("0Romaji"));

        assertFalse(scriber.isAscii("Ελλάς"));
        assertFalse(scriber.isAscii("Romăji"));
        assertFalse(scriber.isAscii("Mосква"));
        assertFalse(scriber.isAscii("日本"));
        assertFalse(scriber.isAscii("エーイチ"));
        assertFalse(scriber.isAscii("しょう"));
    }

}