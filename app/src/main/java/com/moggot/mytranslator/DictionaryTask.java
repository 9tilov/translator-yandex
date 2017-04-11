package com.moggot.mytranslator;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.moggot.mytranslator.translate.Dictionary;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 03.04.17.
 */

public class DictionaryTask extends AsyncTask<Translator, Void, String> {

    private static final String LOG_TAG = "DictionaryTask";
    private Translator translator;
    private Dictionary dictionary;
    private ProgressBar progressBar;
    private Fragment parentFragment;

    public DictionaryTask(Fragment parentFragment) {
        if (parentFragment == null)
            return;
        this.parentFragment = parentFragment;
        dictionary = new Dictionary();

        progressBar = (ProgressBar) parentFragment.getView().findViewById(R.id.spin_kit);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Translator... params) {

        try {
            translator = params[0];
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
        if (result == null) {
            progressBar.setVisibility(View.GONE);
            return;
        }
        translator.setDetails(result);
        Fragment translatorFragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.getView() != null) {
            TextView tvDetails = (TextView) translatorFragment.getView().findViewById(R.id.tvDetails);
            tvDetails.setText(result);
        }
        progressBar.setVisibility(View.GONE);
    }
}
