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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Makes the generic Yandex API calls. Different service classes can then
 * extend this to make the specific service calls.
 */
abstract class YandexTranslatorAPI {

    static final String ENCODING = "UTF-8";

    private static final int MAX_TEXT_LENGTH = 10000;

    private static final String LOG_TAG = "YandexTranslatorAPI";

    static final String PARAM_API_KEY = "key=",
            PARAM_LANG_PAIR = "&lang=",
            PARAM_TEXT = "&text=";

    /**
     * Forms an HTTPS request, sends it using GET method and returns the result of the request as a String.
     *
     * @param url The URL to query for a String response.
     * @return The translated String.
     * @throws Exception on error.
     */
    private static String retrieveResponse(final URL url) throws Exception {
        final HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
        try {
            final int responseCode = uc.getResponseCode();
            final String result = inputStreamToString(uc.getInputStream());
            if (responseCode != 200) {
                switch (responseCode) {
                    case 401:
                        throw new Exception("Wrong API key: " + result);
                    case 402:
                        throw new Exception("API key is locked: " + result);
                    case 404:
                        throw new Exception("Daily text limit exceeded: " + result);
                    case 413:
                        throw new Exception("Maximum text size exceeded: " + result);
                    case 422:
                        throw new Exception("Text can not be translated: " + result);
                    case 501:
                        throw new Exception("This translation direction is not supported: " + result);
                    default:
                        throw new Exception("Unknown error: " + result);
                }
            }
            return result;
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
    }

    /**
     * Forms a request, sends it using the GET method and returns the contents of the array of strings
     * with the given label, with multiple strings concatenated.
     */
    String retrievePropArrString(final URL url) throws Exception {
        final String response = retrieveResponse(url);
        return parse(response);
    }

    protected abstract String parse(final String inputString) throws Exception;

    /**
     * Reads an InputStream and returns its contents as a String.
     * Also effects rate control.
     *
     * @param inputStream The InputStream to read from.
     * @return The contents of the InputStream as a String.
     * @throws Exception on error.
     */
    private static String inputStreamToString(final InputStream inputStream) throws Exception {
        final StringBuilder outputBuilder = new StringBuilder();

        try {
            String string;
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
                while (null != (string = reader.readLine())) {
                    // TODO Can we remove this?
                    // Need to strip the Unicode Zero-width Non-breaking Space. For some reason, the Microsoft AJAX
                    // services prepend this to every response
                    outputBuilder.append(string.replaceAll("\uFEFF", ""));
                }
            }
        } catch (Exception ex) {
            throw new Exception("[yandex-translator-api] Error reading translation stream.", ex);
        }
        return outputBuilder.toString();
    }

    void validateServiceState(final String text, final String apiKey) throws Exception {
        final int byteLength = text.getBytes(ENCODING).length;
        if (byteLength > MAX_TEXT_LENGTH) { // TODO What is the maximum text length allowable for Yandex?
            throw new RuntimeException("TEXT_TOO_LARGE");
        }
        validateKeyState(apiKey);
    }

    //Check if ready to make request, if not, throw a RuntimeException
    private void validateKeyState(String apiKey) throws Exception {
        if (apiKey == null || apiKey.length() < 27) {
            throw new RuntimeException("INVALID_API_KEY - Please set the API Key with your Yandex API Key");
        }
    }

}
