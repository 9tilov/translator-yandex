package com.moggot.mytranslator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.fragments.FavoritesFragment;
import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.fragments.RootFragment;
import com.moggot.mytranslator.fragments.TranslatorFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.FavoritesDisplay;
import com.moggot.mytranslator.observer.HistoryDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private BackAwareEditText etText;

    private Translator translator;
    private TranslatorContext translatorContext;

    private DataBase db;

    private ViewPager pager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etText = (BackAwareEditText) findViewById(R.id.etText);
        db = new DataBase(this);
        createTranslator();

        pager = (ViewPager) findViewById(R.id.pager);
        final SlidePagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        final PagerTabStrip header = (PagerTabStrip) findViewById(R.id.pager_header);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Log.v(LOG_TAG, "onPageSelected");
                Fragment fragment;
                TranslatorData translatorData = new TranslatorData();
                if (position == 0) {
                    fragment = getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
                    if (fragment != null && fragment.getView() != null) {
                        Display display = new HistoryDisplay(MainActivity.this, fragment.getView(), translatorData);
                        translatorData.setTranslator(translator);
                        display.display();
                        return;
                    }
                } else {
                    fragment = (FavoritesFragment) pager.getAdapter().instantiateItem(pager, pager.getCurrentItem());
                    Log.v(LOG_TAG, "view = " + fragment.getView());
                    if (fragment != null && fragment.getView() != null) {
                        Display display = new FavoritesDisplay(MainActivity.this, fragment.getView(), translatorData);
                        translatorData.setTranslator(translator);
                        display.display();
                    }
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, Consts.EXTRA_STATE);
            State state;
            if (fragment instanceof TranslatorFragment) {
                state = new TranslationOn(this);
            } else if (fragment instanceof HistoryFragment) {
                state = new TranslationOff(this);
            } else
                return;
            translatorContext.setState(state);
        }

        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start,
                                      int lengthBefore,
                                      int lengthAfter) {
                pager.setCurrentItem(0);
                if (etText.getText().toString().isEmpty()) {
                    State stateOff = new TranslationOff(MainActivity.this);
                    translatorContext.setState(stateOff);
                    translatorContext.show(translator);
                    header.setVisibility(View.VISIBLE);
                    return;
                }

                if (translatorContext.getState() instanceof TranslationOff) {
                    State stateOn = new TranslationOn(MainActivity.this);
                    translatorContext.setState(stateOn);
                    header.setVisibility(View.GONE);
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
    }

    private void createTranslator() {
        String inputLanguage = LangSharedPreferences.loadInputLanguage(this);
        String outputLanguage = LangSharedPreferences.loadOutputLanguage(this);
        translator = new Translator(null
                , etText.getText().toString()
                , ""
                , inputLanguage
                , outputLanguage
                , false
                , "");

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
    }

    public void onClickClearHistory(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_history));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteAll();
                        translatorContext.show(translator);
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
        Fragment translatorFragment = getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
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

    public void onClickClearFavorites(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_favorites));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteAllFavorites();
                        Fragment fragment = (FavoritesFragment) pager.getAdapter().instantiateItem(pager, pager.getCurrentItem());
                        Log.v(LOG_TAG, "view = " + fragment.getView());
                        if (fragment != null && fragment.getView() != null) {
                            TranslatorData translatorData = new TranslatorData();
                            Display display = new FavoritesDisplay(MainActivity.this, fragment.getView(), translatorData);
                            translatorData.setTranslator(translator);
                            display.display();
                        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_LANGUAGE:
                resetTranslator();
                String inputLang = LangSharedPreferences.loadInputLanguage(this);
                String outputLang = LangSharedPreferences.loadOutputLanguage(this);
                translator.setInputLanguage(inputLang);
                translator.setOutputLanguage(outputLang);

                translatorContext.show(translator);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, Consts.EXTRA_STATE, fragment);
        }
        fragment = getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, Consts.EXTRA_STATE, fragment);
        }
    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {

        static final int NUM_ITEMS = 2;

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*
             * IMPORTANT: This is the point. We create a RootFragment acting as
			 * a container for other fragments
			 */
            if (position == 0) {
                State stateOff = new TranslationOff(MainActivity.this);
                translatorContext.setState(stateOff);
                translatorContext.show(translator);
                return RootFragment.newInstance();
            } else {
                return FavoritesFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            SpannableStringBuilder sb = new SpannableStringBuilder(" ");
            Drawable drawable;
            if (position == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_translate_selected_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_translate_selected_24px);
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                return sb;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_bookmark_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_bookmark_24px);
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
            }
        }
    }

}
