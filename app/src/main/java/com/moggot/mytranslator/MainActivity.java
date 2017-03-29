package com.moggot.mytranslator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.fragments.TranslationFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.observer.TraslatorDisplay;
import com.moggot.mytranslator.translate.Translate;
import com.moggot.mytranslator.translator.Translator;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private BackAwareEditText etText;

    private boolean isTranslationStarted = false;

    private Fragment fragment;
    private FragmentTransaction ft;
    private Translator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (BackAwareEditText) findViewById(R.id.etText);

        ft = getFragmentManager().beginTransaction();
        fragment = new HistoryFragment();
        ft.add(R.id.frgmCont, fragment);
        ft.commit();

        translator = new Translator(null
                , etText.getText().toString()
                , ""
                , getString(R.string.en_short)
                , getString(R.string.ru_short)
                , false);
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
                if (str.toString().isEmpty()) {
                    isTranslationStarted = false;
                    ft = getFragmentManager().beginTransaction();
                    fragment = new HistoryFragment();
                    ft.replace(R.id.frgmCont, fragment);
                    ft.commit();
                } else {
                    if (!isTranslationStarted) {
                        isTranslationStarted = true;
                        fragment = new TranslationFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frgmCont, fragment);
                        ft.commit();
                    }
                    TranslationTask task = new TranslationTask();
                    translator.setText(str.toString());
                    task.execute(translator);
                }
            }
        });

        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.v(LOG_TAG, "Enter pressed");
                }
                return false;
            }
        });

        etText.setBackPressedListener(new BackAwareEditText.BackPressedListener() {
            @Override
            public void onImeBack(BackAwareEditText editText) {
                Log.v(LOG_TAG, "acacac");
            }
        });
    }

    public void onClickChangeLang(View view) {
        String inputLang = LangSharedPreferences.loadInputLanguage(this);
        String outputLang = LangSharedPreferences.loadOutputLanguage(this);
        LangSharedPreferences.saveInputLanguage(this, outputLang);
        LangSharedPreferences.saveOutputLanguage(this, inputLang);

        TranslatorData translatorData = new TranslatorData();
        Display traslatorDisplay = new TraslatorDisplay(this, translatorData);
        inputLang = LangSharedPreferences.loadInputLanguage(this);
        outputLang = LangSharedPreferences.loadOutputLanguage(this);

        translator.setInputLanguage(inputLang);
        translator.setOutputLanguage(outputLang);

        translatorData.setTranslator(translator);
        traslatorDisplay.display();

        Fragment translatorFragment = getFragmentManager().findFragmentById(R.id.frgmCont);
        TextView tvTranslation = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
        if (tvTranslation == null)
            return;
        etText.setText(tvTranslation.getText().toString());
    }

    public void onClickInputLang(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.INPUT.getType());
        startActivity(intent);
    }

    public void onClickOutputLang(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.OUTPUT.getType());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        TranslatorData translatorData = new TranslatorData();
        Display traslatorDisplay = new TraslatorDisplay(this, translatorData);
        String inputLang = LangSharedPreferences.loadInputLanguage(this);
        String outputLang = LangSharedPreferences.loadOutputLanguage(this);
        translator.setInputLanguage(inputLang);
        translator.setOutputLanguage(outputLang);
        translatorData.setTranslator(translator);
        traslatorDisplay.display();

        TranslationTask task = new TranslationTask();
        task.execute(translator);
    }

    private class TranslationTask extends AsyncTask<Translator, String, String> {

        private static final String LOG_TAG = "TranslationTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Translator... params) {
            try {
                Translate.setKey(ApiKeys.YANDEX_API_KEY);
                return Translate.execute(params[0].getText()
                        , Consts.Lang.fromString(params[0].getInputLanguage())
                        , Consts.Lang.fromString(params[0].getOutputLanguage()));
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
            Fragment translatorFragment = getFragmentManager().findFragmentById(R.id.frgmCont);
            TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
            if (tvTranslator == null)
                return;
            tvTranslator.setText(result);
            translator.setTranslation(result);

        }
    }
}
