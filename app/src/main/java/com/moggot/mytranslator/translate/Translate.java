/*
 * Copyright 2013 Robert Theis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moggot.mytranslator.translate;

import android.util.Log;

import com.moggot.mytranslator.ApiKeys;
import com.moggot.mytranslator.Consts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Makes calls to the Yandex machine translation web service API
 */
public final class Translate extends YandexTranslatorAPI {

    private static final String SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static final String apiKey = ApiKeys.YANDEX_API_KEY;

    private static final String LOG_TAG = "Translate";

    /**
     * Translates text from a given Lang to another given Lang using Yandex.
     *
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to   The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public String execute(final String text, final Consts.Lang from, final Consts.Lang to) throws Exception {
        validateServiceState(text, apiKey);
        final String params =
                PARAM_API_KEY + URLEncoder.encode(apiKey, ENCODING) +
                        PARAM_TEXT + URLEncoder.encode(text, ENCODING)
                        + PARAM_LANG_PAIR + URLEncoder.encode(from.toString(), ENCODING) + URLEncoder.encode("-", ENCODING) + URLEncoder.encode(to.toString(), ENCODING);

        final URL url = new URL(SERVICE_URL + params);
        Log.v(LOG_TAG, "url = " + url.toString());
        return retrievePropArrString(url).trim();
    }

    @Override
    protected String parse(final String inputString) throws Exception {
        JSONObject jsonObj = new JSONObject(inputString);
        JSONArray jsonArray = jsonObj.getJSONArray("text");
        return jsonArray.get(0).toString();
    }
}
