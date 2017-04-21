package com.moggot.mytranslator;

/**
 * Created by toor on 24.03.17.
 */

public class Consts {

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

    public static final String SHARED_PREF_NAME = "my_shared_pref";

    public static final String EXTRA_LANG = "extra_lang";
    public static final String EXTRA_TEXT = "extra_text";

    public static final String TAG_FRAGMENT_TRANSLATOR = "tag_fragment_translator";
    public static final String TAG_FRAGMENT_HISTORY = "tag_fragment_history";

    public static final String BASE_URL_TRANSLATION = "https://translate.yandex.net/";
    public static final String BASE_URL_DICTIONARY = "https://dictionary.yandex.net/";


    public static final int REQUEST_CODE_ACTIVITY_LANGUAGE = 0;
}