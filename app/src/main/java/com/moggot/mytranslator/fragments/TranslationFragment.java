package com.moggot.mytranslator.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moggot.mytranslator.ApiKeys;
import com.moggot.mytranslator.BackAwareEditText;
import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.detect.Detect;
import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.observer.LangData;
import com.moggot.mytranslator.observer.TraslatorDisplay;
import com.moggot.mytranslator.translate.Translate;

/**
 * Created by toor on 28.03.17.
 */

public class TranslationFragment extends Fragment {

    private TextView tvTranslation;

    private static final String LOG_TAG = "TranslationFragment";

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traslation, container, false);
        this.context = getActivity();

        tvTranslation = (TextView) view.findViewById(R.id.tvTranslation);
        final BackAwareEditText etText = (BackAwareEditText) getActivity().findViewById(R.id.etText);
        Button btnChange = (Button) getActivity().findViewById(R.id.btnChangeLang);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "fragment");
                Language inputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.INPUT);
                Language outputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.OUTPUT);
                LangSharedPreferences.saveLanguage(context, new Language(inputLang.getName(), Consts.LANG_TYPE.OUTPUT));
                LangSharedPreferences.saveLanguage(context, new Language(outputLang.getName(), Consts.LANG_TYPE.INPUT));

                LangData langData = new LangData();
                TraslatorDisplay adapterDisplay = new TraslatorDisplay(context, langData);
                inputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.INPUT);
                langData.setLanguage(inputLang);
                adapterDisplay.display();
                outputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.OUTPUT);
                langData.setLanguage(outputLang);
                adapterDisplay.display();
                TranslationTask feed = new TranslationTask();
                etText.setText(tvTranslation.getText().toString());
                feed.execute(tvTranslation.getText().toString(), inputLang.getName(), outputLang.getName());

            }
        });
        final Language inputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.INPUT);
        final Language outputLang = LangSharedPreferences.loadLanguage(context, Consts.LANG_TYPE.OUTPUT);
        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable str) {
                TranslationTask feed = new TranslationTask();
                feed.execute(str.toString(), inputLang.getName(), outputLang.getName());
            }
        });

        return view;
    }

    private class TranslationTask extends AsyncTask<String, String, String> {

        private static final String LOG_TAG = "TranslationTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Detect.setKey(ApiKeys.YANDEX_API_KEY);
                publishProgress(Detect.execute(params[0]).toString());
                Translate.setKey(ApiKeys.YANDEX_API_KEY);
                return Translate.execute(params[0], Consts.Lang.fromString(params[1]), Consts.Lang.fromString(params[2]));
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Language inputLanguage = new Language(values[0], Consts.LANG_TYPE.INPUT);
            LangSharedPreferences.saveLanguage(context, inputLanguage);
            LangData langData = new LangData();
            TraslatorDisplay adapterDisplay = new TraslatorDisplay(context, langData);
            langData.setLanguage(inputLanguage);
            adapterDisplay.display();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvTranslation.setText(result);
        }
    }
}
