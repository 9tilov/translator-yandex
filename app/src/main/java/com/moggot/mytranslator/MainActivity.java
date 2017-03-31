package com.moggot.mytranslator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.moggot.mytranslator.adapter.FavoritesAdapter;
import com.moggot.mytranslator.adapter.HistoryAdapter;
import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.fragments.TranslationFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.observer.TraslatorDisplay;
import com.moggot.mytranslator.translate.Translate;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

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

        initTabhost();

        etText = (BackAwareEditText) findViewById(R.id.etText);

        createTranslator();
        ft = getFragmentManager().beginTransaction();
        fragment = new HistoryFragment();
        ft.add(R.id.frgmCont, fragment);
        ft.commit();


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
                    ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_HISTORY);
                    ft.commit();
                } else {
                    if (!isTranslationStarted) {
                        isTranslationStarted = true;
                        createTranslator();
                        fragment = new TranslationFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_TRANSLATOR);
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
                    hideKeyboard();
                    if (etText.getText().toString().isEmpty()) {
                        return false;
                    }
                    saveRecord(translator);
                }
                return false;
            }
        });

        etText.setBackPressedListener(new BackAwareEditText.BackPressedListener() {
            @Override
            public void onImeBack(BackAwareEditText editText) {
                hideKeyboard();
                if (etText.getText().toString().isEmpty())
                    return;
                saveRecord(translator);
            }
        });

        etText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etText.setSelection(etText.getText().length());
                }
            }
        });
    }

    private void initTabhost() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.tabTranslator);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab_selector_translator));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tabFavorites);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab_selector_history));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tag1")) {
                    Fragment historyFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
                    if (historyFragment != null && historyFragment.isVisible()) {
                        ListView listView = (ListView) historyFragment.getView().findViewById(R.id.lvHistory);
                        DataBase db = new DataBase(MainActivity.this);
                        List<Translator> records = db.getAllRecords();
                        HistoryAdapter adapter = new HistoryAdapter(MainActivity.this, records);
                        listView.setAdapter(adapter);
                    }
                }
                if (tabId.equals("tag2")) {
                    ListView listView = (ListView) findViewById(R.id.lvFavorites);
                    DataBase db = new DataBase(MainActivity.this);
                    List<Translator> records = db.getFavoritesRecords();
                    FavoritesAdapter adapter = new FavoritesAdapter(MainActivity.this, records);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private void createTranslator() {
        String inputLanguage = LangSharedPreferences.loadInputLanguage(this);
        String outputLanguage = LangSharedPreferences.loadOutputLanguage(this);
        translator = new Translator(null
                , etText.getText().toString()
                , ""
                , inputLanguage
                , outputLanguage
                , false);
    }

    private void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void saveRecord(Translator translator) {
        Translator tmpTranslator = new Translator(null, translator.getText(), translator.getTranslation()
                , translator.getInputLanguage(), translator.getOutputLanguage(), translator.getIsFavorites());
        DataBase db = new DataBase(this);
        db.addRecord(tmpTranslator);
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

        Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            TextView tvTranslation = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
            etText.setText(tvTranslation.getText().toString());
        }
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

    public void onClickClear(View view) {
        etText.setText("");
    }

    public void onClickAddFavorites(View view) {
        Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            Button btnFavorites = (Button) translatorFragment.getView().findViewById(R.id.btnFavorites);
            if (translator.getIsFavorites()) {
                translator.setIsFavorites(false);
                btnFavorites.setBackgroundResource(R.drawable.ic_bookmark_border_black_24px);
            } else {
                translator.setIsFavorites(true);
                btnFavorites.setBackgroundResource(R.drawable.ic_bookmark_24px);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        String inputLang = LangSharedPreferences.loadInputLanguage(this);
        String outputLang = LangSharedPreferences.loadOutputLanguage(this);
        translator.setInputLanguage(inputLang);
        translator.setOutputLanguage(outputLang);

        TranslatorData translatorData = new TranslatorData();
        Display traslatorDisplay = new TraslatorDisplay(this, translatorData);
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
            Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
            if (translatorFragment != null && translatorFragment.isVisible()) {
                TextView tvTranslator = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
                tvTranslator.setText(result);
                translator.setTranslation(result);
            }
        }
    }
}
