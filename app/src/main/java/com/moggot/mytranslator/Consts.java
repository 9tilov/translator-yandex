package com.moggot.mytranslator;

/**
 * Created by toor on 24.03.17.
 */

public class Consts {

    public enum Lang {
        AFRICAANS("af"),
        ALBANIAN("sq"),
        AMHARIC("am"),
        ARABIC("ar"),
        ARMENIAN("hy"),
        AZERBAIJANI("az"),
        BASHKIR("ba"),
        BASQUE("eu"),
        BELARUSIAN("be"),
        BENGALI("bn"),
        BOSNIAN("bs"),
        BULGARIAN("bg"),
        CATALAN("ca"),
        CEBUANO("ceb"),
        CHINESE("zh"),
        CROATIAN("hr"),
        CZECH("cs"),
        DANISH("da"),
        DUTCH("nl"),
        ENGLISH("en"),
        ESPERANTO("eo"),
        ESTONIAN("et"),
        FINNISH("fi"),
        FRENCH("fr"),
        GALICIAN("gl"),
        GERMAN("de"),
        GEORGIAN("ka"),
        GREEK("el"),
        GUJARATI("gu"),
        HAITIAN("ht"),
        HEBREW("he"),
        HINDI("hi"),
        HUNGARIAN("hu"),
        ICELANDIC("is"),
        INDONESIAN("id"),
        IRISH("ga"),
        ITALIAN("it"),
        JAPANESE("ja"),
        JAVANESE("jv"),
        KANNADA("kn"),
        KAZAKH("kk"),
        KOREAN("ko"),
        KYRGYZ("ky"),
        LATIN("la"),
        LATVIAN("lv"),
        LITHUANIAN("lt"),
        LUXEMBOURGISH("lb"),
        MACEDONIAN("mk"),
        MALAGASY("mg"),
        MALAY("ms"),
        MALAYALAM("ml"),
        MALTESE("mt"),
        MAORI("mi"),
        MARATHI("mr"),
        MARI("mhr"),
        MINING("mrj"),
        MONGOLIAN("mn"),
        NEPALI("ne"),
        NORWEGIAN("no"),
        PAPIAMENTO("pap"),
        PERSIAN("fa"),
        POLISH("pl"),
        PUNDJABI("pa"),
        PORTUGUESE("pt"),
        ROMANIAN("ro"),
        RUSSIAN("ru"),
        SCOTTISH("gd"),
        SCYTHE("xh"),
        SERBIAN("sr"),
        SINHALA("si"),
        SLOVAK("sk"),
        SLOVENIAN("sl"),
        SPANISH("es"),
        SUNDANESE("su"),
        SWAHILI("sw"),
        SWEDISH("sv"),
        TAGALOG("tl"),
        TAJIK("tg"),
        TAMIL("ta"),
        TATAR("tt"),
        TELUGU("te"),
        THAI("th"),
        TURKISH("tr"),
        UDMURT("udm"),
        UKRAINIAN("uk"),
        URDU("ur"),
        UZBEK("uz"),
        VIETNAMESE("vi"),
        WELSH("cy"),
        YIDDISH("yi");

        /**
         * String representation of this language.
         */
        private final String language;

        /**
         * Enum constructor.
         *
         * @param language The language identifier.
         */
        Lang(final String language) {
            this.language = language;
        }

        public static Lang fromString(final String language) {
            for (Lang l : values()) {
                if (l.toString().equals(language)) {
                    return l;
                }
            }
            return null;
        }

        /**
         * Returns the String representation of this language.
         *
         * @return The String representation of this language.
         */
        @Override
        public String toString() {
            return language;
        }

    }

    public enum LANG_TYPE {
        INPUT(0),
        OUTPUT(1);

        private final int type;

        LANG_TYPE(int type) {
            this.type = type;
        }

        public static LANG_TYPE fromInteger(int x) {
            switch (x) {
                case 0:
                    return INPUT;
                case 1:
                    return OUTPUT;
            }
            return null;
        }

        public int getType() {
            return this.type;
        }
    }

    public static final String EXTRA_LANG = "extra_lang";
}