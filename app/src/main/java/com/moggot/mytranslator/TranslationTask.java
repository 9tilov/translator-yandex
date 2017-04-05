package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.moggot.mytranslator.translate.Translate;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationTask extends AsyncTask<Translator, Void, String> {

    private static final String LOG_TAG = "TranslationTask";
    private Context context;
    private Translator translator;
    private Translate translate;

    public TranslationTask(Context context) {
        translate = new Translate();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Translator... params) {
        try {
            translator = params[0];

            return translate.execute(params[0].getText()
                    , Consts.Lang.fromString(params[0].getInputLanguage())
                    , Consts.Lang.fromString(params[0].getOutputLanguage()));
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.v(LOG_TAG, "result = " + result);
        if (result == null)
            return;
        Fragment translatorFragment = ((Activity) context).getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
            tvTranslator.setText(result);
            translator.setTranslation(result);
        }
    }
}
