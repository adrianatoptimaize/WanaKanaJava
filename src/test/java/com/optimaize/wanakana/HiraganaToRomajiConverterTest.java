package com.optimaize.wanakana;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class HiraganaToRomajiConverterTest {

    private final HiraganaToRomajiConverter hiraganaToRomajiConverter = HiraganaToRomajiConverter.getInstance();

    @Test(dataProvider = "simple")
    public void simple(String input, String expected) {
        String latin = hiraganaToRomajiConverter.convert(input);
        assertEquals(latin, expected);
    }
    @DataProvider
    public Object[][] simple() {
        return new Object[][]{
                {"さと", "sato"},
                {"さとう", "satō"},
                {"むかいやま", "mukaiyama"},
                {"ながいかりまち", "nagaikarimachi"},
                {"い, う", "i,u"},
                {"たかとう", "takatō"},
                {"いづみ", "izumi"},
                {"いずみ", "izumi"},
                {"たかとお", "takatō"},
                {"おの", "ono"},

                {"かわえ", "kawae"},
                {"かわうえ", "kawaue"},
                {"まつお", "matsuo"},
                {"まつのお", "matsunō"},
                {"つげうえ", "tsugeue"},

                {"ぎゃ", "gya"},
                {"ちゅ", "chu"},
                {"ちゅう", "chū"},
                {"ぎゅう", "gyū"},
                {"すずき", "suzuki"},
                {"やまぐち", "yamaguchi"},
                {"やまくち", "yamakuchi"},
                {"うえだ", "ueda"},
                {"うえでん", "ueden"},
                {"ごどう", "godō"},
                {"かどわき", "kadowaki"},

                {"ぎょと", "gyoto"},
                {"きょと", "kyoto"},
                {"みょと", "myoto"},
                {"しょと", "shoto"},
                {"じょと", "joto"},
                {"にょと", "nyoto"},
                {"りょと", "ryoto"},
                {"ひおと", "hioto"},
                {"ひょと", "hyoto"},
                {"におと", "nioto"},
                {"ちょと", "choto"},

                {"みうえ", "miue"},
                {"いおう","iō"},
                {"かこう","kakō"},
                {"よぼう","yobō"},
                {"りゅうえん", "ryuuen"},
                {"にしうえはら", "nishiuehara"},

                {"ええいち", "ēichi"},
                {"ぎの", "gino"},


                //converter keeps characters for which it has no conversion.
                {"日本", "日本"}, //Kanji
                {"エーイチ", "エーイチ"}, //Katakana
                {"romaji", "romaji"}, //Latin
                {"Ελλάς", "Ελλάς"}, //Greek
                {"Mосква", "Mосква"}, //Cyrillic
        };
    }

    @Test(dataProvider = "variants")
    public void variants(String input, List<String> expected) {
        List<String> latin = hiraganaToRomajiConverter.convertToVariants(input);
        assertEquals(latin, expected);
    }
    @DataProvider
    public Object[][] variants() {
        return new Object[][]{
                {"うえい", Collections.singletonList("uei")},
                {"みうえ", Collections.singletonList("miue")},
                {"さと", Collections.singletonList("sato")},
                {"ぎの", Collections.singletonList("gino")},
                {"まつお", Collections.singletonList("matsuo")},
                {"かわうえ", Collections.singletonList("kawaue")},
                {"つげうえ", Collections.singletonList("tsugeue")},
                {"りゅうえん", Collections.singletonList("ryuuen")},
                {"にしうえはら", Collections.singletonList("nishiuehara")},

                {"いおう",Arrays.asList("io", "ioh", "ioo")},
                {"かこう",Arrays.asList("kako", "kakoh", "kakoo")},
                {"よぼう",Arrays.asList("yobo", "yoboh", "yoboo")},
                {"まつのお", Arrays.asList("matsuno", "matsunoh", "matsunoo")},
                {"おうの", Arrays.asList("ono", "ohno", "oono")},
                {"おうのちゅう", Arrays.asList("onochu", "ohnochu", "oonochu")},
                {"おおや", Arrays.asList("oya", "ohya", "ooya")},
                {"おおの", Arrays.asList("ono", "ohno", "oono")},
                {"ようだ", Arrays.asList("yoda", "yohda", "yooda")},
                {"どうした", Arrays.asList("doshita", "dohshita", "dooshita")},

                {"たのうえ", Arrays.asList("tanoe", "tanohe", "tanooe")},
                {"こうえん", Arrays.asList("koen", "kohen", "kooen")},
                {"みょうえん", Arrays.asList("myoen", "myohen", "myooen")},

                {"ぎょうと", Arrays.asList("gyoto", "gyohto", "gyooto")},
                {"きょうと", Arrays.asList("kyoto", "kyohto", "kyooto")},
                {"みょうと", Arrays.asList("myoto", "myohto", "myooto")},
                {"しょうと", Arrays.asList("shoto", "shohto", "shooto")},
                {"じょうと", Arrays.asList("joto", "johto", "jooto")},
                {"にょうと", Arrays.asList("nyoto", "nyohto", "nyooto")},
                {"りょうと", Arrays.asList("ryoto", "ryohto", "ryooto")},
                {"ちょうと", Arrays.asList("choto", "chohto", "chooto")},

                {"ちょうと", Arrays.asList("choto", "chohto", "chooto")},

        };
    }

}
