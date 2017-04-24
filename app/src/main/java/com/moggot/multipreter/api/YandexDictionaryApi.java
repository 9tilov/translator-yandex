package com.moggot.multipreter.api;

import com.moggot.multipreter.gson.WordDictionary;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Интерфейс получения объекта с данными от сервера Yandex Dictionary
 */
public interface YandexDictionaryApi {

    /**
     * Получение объекта типа Call<T> с детальными данными перевода
     *
     * @param key  - API-ключ
     * @param text - текст, который необходимо перевести
     * @param lang - направление перевода, например en-ru
     * @return объект с данными
     */
    @POST("api/v1/dicservice.json/lookup")
    Call<WordDictionary> getDetails(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}