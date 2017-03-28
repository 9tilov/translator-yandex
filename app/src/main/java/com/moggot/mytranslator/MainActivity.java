package com.moggot.mytranslator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.observer.LangData;
import com.moggot.mytranslator.observer.TraslatorDisplay;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private BackAwareEditText etText;

    private boolean isTranslationStarted = false;

    private Fragment fragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getFragmentManager().beginTransaction();
        fragment = new HistoryFragment();
        ft.add(R.id.frgmCont, fragment);
        ft.commit();

        etText = (BackAwareEditText) findViewById(R.id.etText);
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
        Log.v(LOG_TAG, "main");
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


}
