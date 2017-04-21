package com.moggot.mytranslator.api;

import com.moggot.mytranslator.gson.WordTranslator;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by toor on 19.04.17.
 */

public interface YandexTranslationApi {

    @POST("api/v1.5/tr.json/translate")
    Call<WordTranslator> getTranslation(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
