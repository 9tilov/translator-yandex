package com.moggot.mytranslator.translate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.moggot.mytranslator.ApiKeys;
import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.YandexTranslatorAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by toor on 03.04.17.
 */

public class Dictionary extends YandexTranslatorAPI {

    private static final String SERVICE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?";
    private static final String apiKey = ApiKeys.YANDEX_DICTIONARY_API_KEY;
    private Context context;

    private static final String LOG_TAG = "Dictionary";

    public Dictionary(Context context) {
        this.context = context;
    }

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
        return retrievePropArrString(url).trim();
    }

    @Override
    protected String parse(final String inputString) throws Exception {
        JSONObject jsonObject = new JSONObject(inputString);
        StringBuilder strResult = new StringBuilder();
        JSONArray mainArray = jsonObject.getJSONArray("def");
        for (int i = 0; i < mainArray.length(); ++i) {
            JSONObject partOfSpeech = mainArray.getJSONObject(i);
            if (i == 0) {
                strResult.append(partOfSpeech.getString("text"));
                strResult.append(" ");
                strResult.append("[");
                strResult.append(partOfSpeech.getString("ts"));
                strResult.append("]");
                strResult.append("\n");
            }
            String strType = partOfSpeech.getString("pos");
            strResult.append(strType);
            strResult.append("\n");
            JSONArray typeArray = partOfSpeech.getJSONArray("tr");
            for (int i_type = 0; i_type < typeArray.length(); ++i_type) {
                JSONObject word = typeArray.getJSONObject(i_type);
                StringBuilder synBuffer = new StringBuilder();
                try {
                    JSONArray synArray = word.getJSONArray("syn");

                    for (int i_syn = 0; i_syn < synArray.length(); ++i_syn) {
                        JSONObject synObj = synArray.getJSONObject(i_syn);

                        synBuffer.append(synObj.getString("text"));
                        synBuffer.append(" ");

                        try {
                            synBuffer.append(synObj.getString("gen"));
                        } catch (Exception e) {
                        }
                        if (i_syn < synArray.length() - 1)
                            synBuffer.append(", ");

                    }
                } catch (Exception e) {
                }

                StringBuilder meanBuffer = new StringBuilder();
                try {
                    JSONArray meanArray = word.getJSONArray("mean");

                    for (int i_mean = 0; i_mean < meanArray.length(); ++i_mean) {
                        JSONObject meanObj = meanArray.getJSONObject(i_mean);

                        if (i_mean == 0)
                            meanBuffer.append("  (");
                        meanBuffer.append(meanObj.getString("text"));

                        if (i_mean < meanArray.length() - 1)
                            meanBuffer.append(", ");
                        if (i_mean == meanArray.length() - 1)
                            meanBuffer.append(")");

                    }
                } catch (Exception e) {
                }

                StringBuilder exTextBuffer = new StringBuilder();

                try {
                    JSONArray exTextArray = word.getJSONArray("ex");

                    for (int i_ex_text = 0; i_ex_text < exTextArray.length(); ++i_ex_text) {
                        JSONObject exTextObj = exTextArray.getJSONObject(i_ex_text);

                        exTextBuffer.append("       ");
                        exTextBuffer.append(exTextObj.getString("text"));
                        exTextBuffer.append(" - \n");
                        JSONArray exTranslateArray = exTextObj.getJSONArray("tr");
                        for (int i_tr_text = 0; i_tr_text < exTranslateArray.length(); ++i_tr_text) {
                            JSONObject exTrObj = exTranslateArray.getJSONObject(i_tr_text);
                            exTextBuffer.append("       ");
                            exTextBuffer.append(exTrObj.getString("text"));
                            if (i_ex_text < exTextArray.length() - 1)
                                exTextBuffer.append("\n");
                        }

                    }
                } catch (Exception e) {
                }

                StringBuilder strWord = new StringBuilder();
                strWord.append(String.valueOf(i_type+ 1));
                strWord.append(" ");
                strWord.append(word.getString("text"));
                String strGen = "";
                try {
                    strGen = word.getString("gen");
                } catch (Exception e) {
                }
                strResult.append("  ");
                strResult.append(strWord.toString());
                if (!strGen.isEmpty()) {
                    strResult.append(" ");
                    strResult.append(strGen);
                }
                if (!synBuffer.toString().isEmpty()) {
                    strResult.append(", ");
                    strResult.append(synBuffer.toString());
                }
                if (!meanBuffer.toString().isEmpty()) {
                    strResult.append("\n");
                    strResult.append("  ");
                    strResult.append(meanBuffer.toString());
                }
                if (!exTextBuffer.toString().isEmpty()) {
                    strResult.append("\n");
                    strResult.append(exTextBuffer.toString());
                }

                strResult.append("\n");


            }
        }
        Log.v(LOG_TAG, strResult.toString());
        return strResult.toString();
    }

    private static String retrieveResponse(final URL url) throws Exception {
        final HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();

        try {
            final int responseCode = uc.getResponseCode();
            final String result = inputStreamToString(uc.getInputStream());
            if (responseCode != 200) {
                throw new Exception("Error from Yandex API: " + result);
            }
            return result;
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
    }

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
}
