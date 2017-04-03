package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.moggot.mytranslator.translate.Dictionary;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 03.04.17.
 */

public class DictionaryTask extends AsyncTask<Translator, Void, String> {

    private static final String LOG_TAG = "DictionaryTask";
    private Context context;
    private Dictionary dictionary;

    public DictionaryTask(Context context) {
        dictionary = new Dictionary(context);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Translator... params) {
        try {
            return dictionary.execute(params[0].getText()
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
            TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvDictionary);
            tvTranslator.setText(result);
        }
    }
}
