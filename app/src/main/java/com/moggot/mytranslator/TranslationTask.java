package com.moggot.mytranslator;

import android.os.AsyncTask;
import android.util.Log;

import com.moggot.mytranslator.Detect.Detect;
import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.translate.Translate;

/**
 * Created by toor on 23.03.17.
 */

public class TranslationTask extends AsyncTask<Void, Void, Void> {

    private static final String LOG_TAG = "TranslationTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Translate.setKey(ApiKeys.YANDEX_API_KEY);
            String translation = Translate.execute("hello mother father sister", Language.ENGLISH, Language.RUSSIAN);
            Log.v(LOG_TAG, "transition1 = " + translation);

            Language language = Detect.execute("sveiki,");
            Log.v(LOG_TAG, "transition2 = " + language.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
