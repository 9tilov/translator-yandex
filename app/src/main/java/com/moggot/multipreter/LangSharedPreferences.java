package com.moggot.multipreter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс хранения входного и выходного языков
 * Было принято решение использовать SharedPrefernces, а не SQLite
 * для хранения язков перевода, т.к. не хочется стрелять из пушки
 * по воробьям. Языка перевода удобнее хранить в SharedPreferences,
 * не создавая при этом дополнительной таблицы в БД.
 */
public class LangSharedPreferences {

    private static final String LOG_TAG = "LangSharedPreferences";

    /**
     * Ключ для хранения входного языка
     */
    private static final String inputLang = "inputLanguage";

    /**
     * Ключ для хранения выходного языка
     */
    private static final String outputLang = "outputLanguage";

    /**
     * Сохранение входного языка
     *
     * @param context  - контекст Activity
     * @param language - код входного языка
     */
    public static void saveInputLanguage(Context context, String language) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_LANG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(inputLang);
        editor.putString(inputLang, language);
        editor.apply();
    }

    /**
     * Сохранение выходного языка
     *
     * @param context  - контекст Activity
     * @param language - код выходного языка
     */
    public static void saveOutputLanguage(Context context, String language) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_LANG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(outputLang);
        editor.putString(outputLang, language);
        editor.apply();
    }

    /**
     * Загрузка входного языка
     *
     * @param context - контекст Activity
     * @return входной язык
     */
    public static String loadInputLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_LANG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(inputLang, context.getString(R.string.en_short));
    }

    /**
     * Загрузка выходного языка
     *
     * @param context - контекст Activity
     * @return выходной язык
     */
    public static String loadOutputLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_LANG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(outputLang, context.getString(R.string.ru_short));
    }
}
