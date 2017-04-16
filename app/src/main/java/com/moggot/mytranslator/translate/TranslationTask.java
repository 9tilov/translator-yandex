package com.moggot.mytranslator.translate;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translate.Translate;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationTask extends AsyncTask<Translator, Void, String> {

    private static final String LOG_TAG = "TranslationTask";
    private Translator translator;
    private Translate translate;
    private Fragment parentFragment;

    public TranslationTask(Fragment parentFragment) {
        if (parentFragment == null)
            return;
        this.parentFragment = parentFragment;
        translate = new Translate();
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
        translator.setTranslation(result);
        Fragment translatorFragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.getView() != null) {
            TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
            tvTranslator.setText(result);
        }
    }
}
