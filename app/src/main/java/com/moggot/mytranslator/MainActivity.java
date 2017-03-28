package com.moggot.mytranslator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.observer.LangData;
import com.moggot.mytranslator.observer.TraslatorDisplay;
import com.moggot.mytranslator.translate.Translate;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";

    private TextView tvInputLang, tvOutputLang, tvTranslation;

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInputLang = (TextView) findViewById(R.id.tvInputLang);
        tvOutputLang = (TextView) findViewById(R.id.tvOutputLang);
        tvTranslation = (TextView) findViewById(R.id.tvTranslation);

        etText = (EditText) findViewById(R.id.etText);
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
                Language inputLang = LangSharedPreferences.loadLanguage(getApplication(), Consts.LANG_TYPE.INPUT);
                Language outputLang = LangSharedPreferences.loadLanguage(getApplication(), Consts.LANG_TYPE.OUTPUT);
                feed.execute(str.toString(), inputLang.getName(), outputLang.getName());
            }
        });
    }

    public void onClickChangeLang(View view) {
        Language inputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.INPUT);
        Language outputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.OUTPUT);
        LangSharedPreferences.saveLanguage(this, new Language(inputLang.getName(), Consts.LANG_TYPE.OUTPUT));
        LangSharedPreferences.saveLanguage(this, new Language(outputLang.getName(), Consts.LANG_TYPE.INPUT));

        LangData langData = new LangData();
        TraslatorDisplay adapterDisplay = new TraslatorDisplay(this, langData);
        inputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.INPUT);
        langData.setLanguage(inputLang);
        adapterDisplay.display();
        outputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.OUTPUT);
        langData.setLanguage(outputLang);
        adapterDisplay.display();
    }

    public void onClickInputLang(View view) {
        Intent intent = new Intent(this, LangActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.INPUT.getType());
        startActivity(intent);
    }

    public void onClickOutputLang(View view) {
        Intent intent = new Intent(this, LangActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.OUTPUT.getType());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        LangData langData = new LangData();
        TraslatorDisplay adapterDisplay = new TraslatorDisplay(this, langData);
        Language inputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.INPUT);
        langData.setLanguage(inputLang);
        adapterDisplay.display();
        Language outputLang = LangSharedPreferences.loadLanguage(this, Consts.LANG_TYPE.OUTPUT);
        langData.setLanguage(outputLang);
        adapterDisplay.display();
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
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvTranslation.setText(result);
        }
    }
}
