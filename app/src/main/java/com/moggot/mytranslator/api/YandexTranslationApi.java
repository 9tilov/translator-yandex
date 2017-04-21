package com.moggot.mytranslator.api;

import com.moggot.mytranslator.gson.WordTranslator;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Интерфейс получения объекта с данными от сервера Yandex Translator
 */

public interface YandexTranslationApi {

    /**
     * Получение объекта типа Call<T> с переводом слова
     *
     * @param key  - API-ключ
     * @param text - текст, который необходимо перевести
     * @param lang - направление перевода, например en-ru
     * @return объект с данными
     */
    @POST("api/v1.5/tr.json/translate")
    Call<WordTranslator> getTranslation(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
