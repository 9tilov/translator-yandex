package com.moggot.mytranslator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.moggot.mytranslator.language.Language;

/**
 * Created by toor on 24.03.17.
 */

public class LangSharedPreferences {

    private static final String inputLang = "inputLanguage";
    private static final String outputLang = "outputLanguage";

    public static void saveLanguage(Context context, Language language) {
        android.content.SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (language.getType()) {
            case INPUT:
                editor.remove(inputLang);
                editor.putString(inputLang, language.getName());
                break;
            case OUTPUT:
                editor.remove(outputLang);
                editor.putString(outputLang, language.getName());
                break;
            default:
                break;
        }
        editor.apply();
    }

    public static Language loadLanguage(Context context, Consts.LANG_TYPE type) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        switch (type) {
            case INPUT:
                return new Language(sharedPreferences.getString(inputLang, "en"), type);
            case OUTPUT:
                return new Language(sharedPreferences.getString(outputLang, "ru"), type);
            default:
                return new Language(context.getString(R.string.en), Consts.LANG_TYPE.INPUT);
        }
    }
}
