package com.moggot.multipreter;

/**
 * Класс констант приложения
 */
public class Consts {

    /**
     * Тип языка
     * 0 - входной язык
     * 1 - выходной язык
     */
    public enum LANG_TYPE {
        INPUT(0),
        OUTPUT(1);

        private final int type;

        LANG_TYPE(int type) {
            this.type = type;
        }

        /**
         * Получение типа по его коду
         *
         * @param code - код типа языка
         * @return тип языка
         */
        public static LANG_TYPE fromInteger(int code) {
            switch (code) {
                case 0:
                    return INPUT;
                case 1:
                    return OUTPUT;
            }
            return null;
        }

        /**
         * Получение типа языка
         *
         * @return тип языка
         */
        public int getType() {
            return this.type;
        }
    }

    /**
     * Имя {@link android.content.SharedPreferences} для хранения языка
     */
    public static final String SHARED_PREF_LANG = "shared_pref_lang";

    /**
     * Ключ для хранения языка при передаче из одного Activity в другой
     */
    public static final String EXTRA_LANG = "extra_lang";

    /**
     * Ключ для хранения текста в Edittext для его восстановления после смены состояния Activity
     */
    public static final String EXTRA_TEXT = "extra_text";

    /**
     * тэг для фрагмента {@link com.moggot.multipreter.fragments.TranslatorFragment}
     */
    public static final String TAG_FRAGMENT_TRANSLATOR = "tag_fragment_translator";

    /**
     * тэг для фрагмента {@link com.moggot.multipreter.fragments.HistoryListFragment}
     */
    public static final String TAG_FRAGMENT_HISTORY = "tag_fragment_history";

    /**
     * Базовый адрес сервера для обычногго перевода
     */
    public static final String BASE_URL_TRANSLATION = "https://translate.yandex.net/";

    /**
     * Базовый адрес сервера для детального перевода
     */
    public static final String BASE_URL_DICTIONARY = "https://dictionary.yandex.net/";

    /**
     * Код для возврата из одного Activity в другой
     */
    public static final int REQUEST_CODE_ACTIVITY_LANGUAGE = 0;

    /**
     * Firebase id
     */
    public final static String FIREBASE_ITEM_ID = "ID";

    /**
     * Firebase name
     */
    public final static String FIREBASE_ITEM_NAME = "NAME";

    /**
     * Firebase image
     */
    public final static String FIREBASE_CONTENT_TYPE = "IMAGE";
}