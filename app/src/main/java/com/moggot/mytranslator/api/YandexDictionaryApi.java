package com.moggot.mytranslator.api;

import com.moggot.mytranslator.gson.WordDictionary;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by toor on 19.04.17.
 */

public interface YandexDictionaryApi {

    @POST("api/v1/dicservice.json/lookup")
    Call<WordDictionary> getDetails(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}