package com.moggot.multipreter;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.moggot.multipreter.api.DetailedTranslationApi;
import com.moggot.multipreter.api.YandexTranslationApi;

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
    private static DetailedTranslationApi detailedTranslationApi;

    /**
     * Tracker отслеживания
     */
    private Tracker mTracker;

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
        detailedTranslationApi = retrofitDictionary.create(DetailedTranslationApi.class);
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
    public static DetailedTranslationApi getDetailedTranslationApi() {
        return detailedTranslationApi;
    }

    /**
     * Получает счетчик {@link Tracker}, используемый по умолчанию для этого приложения {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}
