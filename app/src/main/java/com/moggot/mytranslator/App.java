package com.moggot.mytranslator;

import android.app.Application;

import com.moggot.mytranslator.api.YandexDictionaryApi;
import com.moggot.mytranslator.api.YandexTranslationApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by toor on 19.04.17.
 */

public class App extends Application {

    private static YandexTranslationApi yandexTranslationApi;
    private static YandexDictionaryApi yandexDictionaryApi;

    Retrofit retrofitTranslation, retrofitDictionary;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofitTranslation = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL_TRANSLATION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yandexTranslationApi = retrofitTranslation.create(YandexTranslationApi.class);

        retrofitDictionary = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL_DICTIONARY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yandexDictionaryApi = retrofitDictionary.create(YandexDictionaryApi.class);
    }

    public static YandexTranslationApi getYandexTranslationApi() {
        return yandexTranslationApi;
    }

    public static YandexDictionaryApi getYandexDictionaryApi() {
        return yandexDictionaryApi;
    }
}
