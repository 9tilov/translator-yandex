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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.moggot.mytranslator.adapter.FavoritesAdapter;
import com.moggot.mytranslator.adapter.HistoryAdapter;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.observer.TraslatorDisplay;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private BackAwareEditText etText;

    private Translator translator;
    private TranslatorContext translatorContext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabhost();

        etText = (BackAwareEditText) findViewById(R.id.etText);

        createTranslator();

        initWindow();

        State stateOff = new TranlationOff(MainActivity.this);
        translatorContext.setState(stateOff);

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
                State stateOn = new TranslationOn(MainActivity.this);
                translatorContext.setState(stateOn);
                translator.setText(str.toString());
                translatorContext.show(translator);
            }
        });

        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (etText.getText().toString().isEmpty()) {
                        return false;
                    }
                    saveRecord();
                }
                return false;
            }
        });

        etText.setBackPressedListener(new BackAwareEditText.BackPressedListener() {
            @Override
            public void onImeBack(BackAwareEditText editText) {
                if (etText.getText().toString().isEmpty())
                    return;
                saveRecord();
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

    private void saveRecord() {
        if (etText.getText().toString().isEmpty())
            return;
        DataBase db = new DataBase(MainActivity.this);
        db.addRecord(translator);
        createTranslator();
    }

    private void initTabhost() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(R.string.tag_translator));

        tabSpec.setContent(R.id.tabTranslator);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab_selector_translator));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getString(R.string.tag_favorites));
        tabSpec.setContent(R.id.tabFavorites);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab_selector_history));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                DataBase db = new DataBase(MainActivity.this);
                if (tabId.equals(getString(R.string.tag_translator))) {
                    Fragment historyFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
                    if (historyFragment != null && historyFragment.isVisible()) {
                        ListView listView = (ListView) historyFragment.getView().findViewById(R.id.lvHistory);
                        List<Translator> records = db.getAllRecords();
                        HistoryAdapter adapter = new HistoryAdapter(MainActivity.this, records);
                        listView.setAdapter(adapter);
                    }
                }
                if (tabId.equals(getString(R.string.tag_favorites))) {
                    ListView listView = (ListView) findViewById(R.id.lvFavorites);
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
        translatorContext = new TranslatorContext(this, translator);
    }

    public void onClickChangeLang(View view) {
        String inputLang = LangSharedPreferences.loadInputLanguage(this);
        String outputLang = LangSharedPreferences.loadOutputLanguage(this);
        LangSharedPreferences.saveInputLanguage(this, outputLang);
        LangSharedPreferences.saveOutputLanguage(this, inputLang);

        inputLang = LangSharedPreferences.loadInputLanguage(this);
        outputLang = LangSharedPreferences.loadOutputLanguage(this);

        translator.setInputLanguage(inputLang);
        translator.setOutputLanguage(outputLang);

        initWindow();

        Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            TextView tvTranslation = (TextView) translatorFragment.getView().findViewById(R.id.tvTranslation);
            etText.setText(tvTranslation.getText().toString());
        }

        saveRecord();
    }

    public void onClickInputLang(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.INPUT.getType());
        startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_LANGUAGE);
        saveRecord();
    }

    public void onClickOutputLang(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.OUTPUT.getType());
        startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_LANGUAGE);
        saveRecord();
    }

    public void onClickClear(View view) {
        etText.setText("");
        State stateOff = new TranlationOff(MainActivity.this);
        translatorContext.setState(stateOff);
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

            DataBase db = new DataBase(this);
            db.addRecord(translator);

        }
    }

    private void initWindow() {
        TranslatorData translatorData = new TranslatorData();
        Display traslatorDisplay = new TraslatorDisplay(this, translatorData);
        translatorData.setTranslator(translator);
        traslatorDisplay.display();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_LANGUAGE:
                String inputLang = LangSharedPreferences.loadInputLanguage(this);
                String outputLang = LangSharedPreferences.loadOutputLanguage(this);
                translator.setInputLanguage(inputLang);
                translator.setOutputLanguage(outputLang);

                initWindow();

                State stateOn = new TranslationOn(MainActivity.this);
                translatorContext.setState(stateOn);
                translator.setText(etText.getText().toString());
                translatorContext.show(translator);
                saveRecord();

                break;
        }
    }
}
