package com.moggot.mytranslator;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by toor on 24.03.17.
 */

public class SharedPreferences {

    private static final String inputLang = "inputLanguage";
    private static final String outputLang = "outputLanguage";

    public static void SaveInputLanguage(Context context, String inputLanguage) {
        android.content.SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(inputLang);
        editor.putString(inputLang, inputLanguage);
        editor.apply();
    }

    public static String LoadInputLanguage(Context context) {
        android.content.SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        return sharedPreferences.getString(inputLang, "en");
    }

    public static void SaveOutputLanguage(Context context, String outputLanguage) {
        android.content.SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(outputLang);
        editor.putString(outputLang, outputLanguage);
        editor.apply();
    }

    public static String LoadOutputLanguage(Context context) {
        android.content.SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        return sharedPreferences.getString(outputLang, "en");
    }


}
