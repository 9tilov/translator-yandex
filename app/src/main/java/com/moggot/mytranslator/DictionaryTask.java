package com.moggot.mytranslator;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.moggot.mytranslator.translate.Dictionary;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 03.04.17.
 */

public class DictionaryTask extends AsyncTask<Translator, Void, String> {

    private static final String LOG_TAG = "DictionaryTask";
    private Context context;
    private Translator translator;
    private Dictionary dictionary;
    private ProgressBar progressBar;

    public DictionaryTask(Context context) {
        dictionary = new Dictionary();
        this.context = context;

        progressBar = (ProgressBar) ((Activity) context).findViewById(R.id.spin_kit);
        CubeGrid cubeGrid = new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
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
        Fragment translatorFragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvDetails);
            tvTranslator.setText(result);
            translator.setDetails(result);
        }
        progressBar.setVisibility(View.GONE);
    }
}
