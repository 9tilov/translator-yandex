package com.moggot.mytranslator;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by toor on 24.03.17.
 */

public class LangSharedPreferences {

    private static final String inputLang = "inputLanguage";
    private static final String outputLang = "outputLanguage";

    private static final String LOG_TAG = "LangSharedPreferences";

    public static void saveInputLanguage(Context context, String language) {
        Log.v(LOG_TAG, "context = " + context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(inputLang);
        editor.putString(inputLang, language);
        editor.apply();
    }

    public static void saveOutputLanguage(Context context, String language) {
        Log.v(LOG_TAG, "context = " + context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(outputLang);
        editor.putString(outputLang, language);
        editor.apply();
    }

    public static String loadInputLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(inputLang, "en");
    }

    public static String loadOutputLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(outputLang, "ru");
    }

}
