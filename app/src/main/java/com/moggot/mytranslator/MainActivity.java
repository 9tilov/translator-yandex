package com.moggot.mytranslator;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.observer.LanguageDisplay;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private BackAwareEditText etText;

    private Translator translator;
    private TranslatorContext translatorContext;

    private boolean isTranslationStarted = false;

    private DataBase db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etText = (BackAwareEditText) findViewById(R.id.etText);
        db = new DataBase(this);
        createTranslator();

        initWindow();

        State stateOff = new TranslationOff(this);
        translatorContext.setState(stateOff);

        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start,
                                      int lengthBefore,
                                      int lengthAfter) {
                if (lengthAfter == lengthBefore) {
                    etText.setSelection(etText.getText().length());
                    return;
                }
                if (!etText.getText().toString().isEmpty())
                    ((Button) findViewById(R.id.btnClearText)).setVisibility(View.VISIBLE);
                else {
                    ((Button) findViewById(R.id.btnClearText)).setVisibility(View.GONE);
                    State stateOff = new TranslationOff(MainActivity.this);
                    translatorContext.setState(stateOff);
                    isTranslationStarted = false;
                    return;

                }
                if (!isTranslationStarted) {
                    State stateOn = new TranslationOn(MainActivity.this);
                    translatorContext.setState(stateOn);
                    isTranslationStarted = true;
                }
                resetTranslator();
                translator.setText(cs.toString());
                translatorContext.show(translator);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable str) {
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
        if (translator.getText().isEmpty())
            return;
        db.addRecord(translator);
        Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            TranslatorData translatorData = new TranslatorData();
            Display traslatorDisplay = new TranslationDisplay(this, translatorFragment.getView(), translatorData);
            translatorData.setTranslator(translator);
            traslatorDisplay.display();
        }
        resetTranslator();
    }

    private void initTabhost() {
        final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);

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
        tabHost.setOnTabChangedListener(new AnimatedTabHostListener(this, tabHost));
    }

    private void createTranslator() {
        translator = Translator.getInstance();
        String inputLanguage = LangSharedPreferences.loadInputLanguage(this);
        String outputLanguage = LangSharedPreferences.loadOutputLanguage(this);

        translator.setInputLanguage(inputLanguage);
        translator.setOutputLanguage(outputLanguage);
        translatorContext = new TranslatorContext(translator);
    }

    private void resetTranslator() {
        translator.setId(null);

        translator.setText(etText.getText().toString());
        translator.setTranslation("");
        translator.setInputLanguage(LangSharedPreferences.loadInputLanguage(this));
        translator.setOutputLanguage(LangSharedPreferences.loadOutputLanguage(this));
        translator.setIsFavorites(false);
        translator.setDetails("");

        Fragment translatorFragment = getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (translatorFragment != null && translatorFragment.isVisible()) {
            ((Button) translatorFragment.getView().findViewById(R.id.btnFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_black_24px);
        }
    }

    public void onClickChangeLang(View view) {
        resetTranslator();
        String inputLang = LangSharedPreferences.loadInputLanguage(this);
        String outputLang = LangSharedPreferences.loadOutputLanguage(this);
        LangSharedPreferences.saveInputLanguage(this, outputLang);
        LangSharedPreferences.saveOutputLanguage(this, inputLang);

        inputLang = LangSharedPreferences.loadInputLanguage(this);
        outputLang = LangSharedPreferences.loadOutputLanguage(this);

        translator.setInputLanguage(inputLang);
        translator.setOutputLanguage(outputLang);

        setLanguages();

        State stateOn = new TranslationOn(this);
        translatorContext.setState(stateOn);
        translator.setText(etText.getText().toString());
        translatorContext.show(translator);

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
        saveRecord();
        etText.setText("");
        State stateOff = new TranslationOff(this);
        translatorContext.setState(stateOff);
    }

    public void onClickDeleteAll(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_history));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteAll();
                        State stateOff = new TranslationOff(MainActivity.this);
                        translatorContext.setState(stateOff);
                        ((Button) findViewById(R.id.btnDeleteAllHistory)).setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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

            if (db.findRecord(translator) != null)
                db.editRecord(translator);
            else
                db.addRecord(translator);
        }
    }

    public void onClickDeleteFavorites(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_favorites));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteAllFavorites();
                        ListView listView = (ListView) findViewById(R.id.lvFavorites);
                        List<Translator> records = db.getFavoritesRecords();
                        FavoritesAdapter adapter = new FavoritesAdapter(MainActivity.this, records);
                        listView.setAdapter(adapter);
                        ((Button) findViewById(R.id.btnDeleteAllFavorites)).setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setLanguages() {
        TranslatorData translatorData = new TranslatorData();
        Display traslatorDisplay = new LanguageDisplay(this, translatorData);
        translatorData.setTranslator(translator);
        traslatorDisplay.display();
    }

    private void initWindow() {
        initTabhost();
        setLanguages();

        if (etText.getText().toString().isEmpty())
            ((Button) findViewById(R.id.btnClearText)).setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_LANGUAGE:
                String inputLang = LangSharedPreferences.loadInputLanguage(this);
                String outputLang = LangSharedPreferences.loadOutputLanguage(this);
                translator.setInputLanguage(inputLang);
                translator.setOutputLanguage(outputLang);

                setLanguages();

                State stateOn = new TranslationOn(this);
                translatorContext.setState(stateOn);
                translator.setText(etText.getText().toString());
                translatorContext.show(translator);

                break;
        }
    }
}
