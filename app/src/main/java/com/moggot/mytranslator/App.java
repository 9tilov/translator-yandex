package com.moggot.mytranslator;

import android.app.Application;

import com.moggot.mytranslator.api.YandexDictionaryApi;
import com.moggot.mytranslator.api.YandexTranslationApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс инициализации Retrofit и объектов интерфейса
 */
public class App extends Application {

    /**
     * Интерфейс с данными сервера обычного перевода
     */
    private static YandexTranslationApi yandexTranslationApi;

    /**
     * Интерфейс с данными сервера детального перевода
     */
    private static YandexDictionaryApi yandexDictionaryApi;

    /**
     * Инициализация Retrofit
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofitTranslation = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL_TRANSLATION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yandexTranslationApi = retrofitTranslation.create(YandexTranslationApi.class);

        Retrofit retrofitDictionary = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL_DICTIONARY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yandexDictionaryApi = retrofitDictionary.create(YandexDictionaryApi.class);
    }

    /**
     * Получение интерфейса
     *
     * @return Интерфейс с данными сервера обычного перевода
     */
    public static YandexTranslationApi getYandexTranslationApi() {
        return yandexTranslationApi;
    }

    /**
     * Получение интерфейса
     *
     * @return Интерфейс с данными сервера детального перевода
     */
    public static YandexDictionaryApi getYandexDictionaryApi() {
        return yandexDictionaryApi;
    }
}
