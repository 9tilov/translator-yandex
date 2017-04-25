package com.moggot.multipreter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.moggot.multipreter.App;
import com.moggot.multipreter.BackAwareEditText;
import com.moggot.multipreter.Consts;
import com.moggot.multipreter.DataBase;
import com.moggot.multipreter.LangSharedPreferences;
import com.moggot.multipreter.LanguageActivity;
import com.moggot.multipreter.MainActivity;
import com.moggot.multipreter.R;
import com.moggot.multipreter.state.State;
import com.moggot.multipreter.state.TranslationOff;
import com.moggot.multipreter.state.TranslationOn;
import com.moggot.multipreter.state.TranslatorContext;
import com.moggot.multipreter.animation.AnimationBounce;
import com.moggot.multipreter.animation.ClearButtonAnimationBounce;
import com.moggot.multipreter.observer.Display;
import com.moggot.multipreter.observer.TranslationDisplay;
import com.moggot.multipreter.observer.TranslatorData;
import com.moggot.multipreter.translator.Translator;

import java.util.Calendar;
import java.util.Date;

/**
 * Класс рутового фрагмента, в котором хранятся фрагменты {@link HistoryListFragment} и {@link TranslatorFragment}
 * Фрагмент {@link FavoritesListFragment} является для него соседним
 */
public class RootFragment extends Fragment implements HistoryListFragment.HistoryListEventListener
        , TranslatorFragment.TranslatorEventListener
        , FavoritesListFragment.FavoritesListEventListener {

    private static final String LOG_TAG = "RootFragment";

    /**
     * транслятор
     */
    private Translator translator;

    /**
     * Tracker для отслеживания
     */
    private Tracker tracker;

    /**
     * Контекст транслятора
     */
    private TranslatorContext translatorContext;

    /**
     * Поле для ввода текста, который нужно перевести
     */
    private BackAwareEditText etText;

    /**
     * База данных
     */
    private DataBase db;

    /**
     * Конструктор
     */
    public RootFragment() {
    }

    /**
     * Метод для создания экземпляра фрагмента
     *
     * @return текущий фрагмент
     */
    public static Fragment newInstance() {
        return new RootFragment();
    }

    /**
     * Фрагмент не будет пересоздан при смене состояния Activity
     * Подготавливаем БД для работы
     *
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        db = new DataBase(getContext());
    }

    /**
     * Отображаем данные фрагмента
     *
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tracker = ((App) getActivity().getApplication()).getDefaultTracker();
    }

    /**
     * Загрузка интерфейса фрагмента
     *
     * @param inflater           - разметка фрагмента
     * @param container          - родительский контейнер фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_root, container, false);
    }

    /**
     * Вызывается после создания фрагмента
     * Здесь происходит инициализация начального состояния транслятора,
     * загрузка его текущего состояния при необходимости,
     * обработка нажатий кнопок и обработка ввода текста пользователем для перевода
     *
     * @param view               - коренвое view фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etText = (BackAwareEditText) view.findViewById(R.id.etText);


        if (savedInstanceState != null)
            etText.setText(savedInstanceState.getString(Consts.EXTRA_TEXT));
        State stateOff = new TranslationOff(this);
        translator = createTranslator();
        translatorContext = createTranslatorContext(translator);
        translatorContext.setState(stateOff);
        translatorContext.show();

        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start,
                                      int lengthBefore,
                                      int lengthAfter) {

                //блок для обработки случая, когда поле ввода пусто
                if (etText.getText().toString().isEmpty()) {
                    resetTranslator(translator);
                    State stateOff = new TranslationOff(RootFragment.this);
                    translatorContext.setState(stateOff);
                    translatorContext.show();
                    return;
                }

                //если состояние Off, то оно переводится в On
                if (translatorContext.getState() instanceof TranslationOff) {
                    State stateOn = new TranslationOn(RootFragment.this);
                    translatorContext.setState(stateOn);
                }
                resetTranslator(translator);
                translator.setText(cs.toString().trim());
                translatorContext.show();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable str) {
            }
        });

        //обработка нажатия кнопки Done на клавиатуре
        etText.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    saveOrEditRecord(translator);
                }
                return false;
            }
        });

        //обработка нажатия кнопки back, когда клавиатура видна
        etText.setBackPressedListener(new BackAwareEditText.BackPressedListener() {
            @Override
            public void onImeBack(BackAwareEditText editText) {
                saveOrEditRecord(translator);
            }
        });

        //перевод курсора в конец строки при фокусе
        etText.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etText.setSelection(etText.getText().length());
                }
            }
        });

        //обработка нажатия кнопки смены языка
        Button btnChangeLang = (Button) view.findViewById(R.id.btnChangeLang);
        btnChangeLang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.change_lang);
                view.startAnimation(bounce);

                resetTranslator(translator);

                String inputLang = LangSharedPreferences.loadInputLanguage(getContext());
                String outputLang = LangSharedPreferences.loadOutputLanguage(getContext());
                LangSharedPreferences.saveInputLanguage(getContext(), outputLang);
                LangSharedPreferences.saveOutputLanguage(getContext(), inputLang);

                inputLang = LangSharedPreferences.loadInputLanguage(getContext());
                outputLang = LangSharedPreferences.loadOutputLanguage(getContext());

                translator.setInputLanguage(inputLang);
                translator.setOutputLanguage(outputLang);
                translator.setText(etText.getText().toString());

                translatorContext.show();
            }
        });

        //обработка нажатия кнопки очистки поля ввода текста
        Button btnClearText = (Button) view.findViewById(R.id.btnClearText);
        btnClearText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveOrEditRecord(translator);
                AnimationBounce animation = new ClearButtonAnimationBounce(getContext());
                animation.animate(view);
                resetTranslator(translator);
            }
        });

        //обработка нажатия кнопки входного языка
        TextView tvInputLang = (TextView) view.findViewById(R.id.tvInputLang);
        tvInputLang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LanguageActivity.class);
                intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.INPUT.getType());
                startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_LANGUAGE);
                saveOrEditRecord(translator);
            }
        });

        //обработка нажатия кнопки выходного языка
        TextView tvOutputLang = (TextView) view.findViewById(R.id.tvOutputLang);
        tvOutputLang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LanguageActivity.class);
                intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.OUTPUT.getType());
                startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_LANGUAGE);
                saveOrEditRecord(translator);
            }
        });
    }

    /**
     * Создание транслятора
     *
     * @return новый транслятор
     */
    private Translator createTranslator() {
        String inputLanguage = LangSharedPreferences.loadInputLanguage(getContext());
        String outputLanguage = LangSharedPreferences.loadOutputLanguage(getContext());
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        return new Translator(null
                , ""
                , ""
                , inputLanguage
                , outputLanguage
                , false
                , ""
                , date);
    }

    /**
     * Создание контекста транслятора
     *
     * @param translator - транслятор
     * @return контекст транслятора для смены состояния
     */
    private TranslatorContext createTranslatorContext(Translator translator) {
        return new TranslatorContext(getContext(), translator);
    }

    /**
     * Сброс данных транслятора
     * Данный метод нужен для сброса данных транслятора,
     * в том числе его ID при каждом новом вводимом символе
     *
     * @param translator - транслятор
     */
    private void resetTranslator(Translator translator) {
        translator.setId(null);
        translator.setText("");
        translator.setTranslation("");
        translator.setInputLanguage(LangSharedPreferences.loadInputLanguage(getContext()));
        translator.setOutputLanguage(LangSharedPreferences.loadOutputLanguage(getContext()));
        translator.setIsFavorites(false);
        translator.setDetails("");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        translator.setDate(date);
    }

    /**
     * Сохранение транслятора, либо его перезапись
     *
     * @param translator - транслятор
     */
    private void saveOrEditRecord(Translator translator) {
        if (translator.getText().isEmpty() || translator.getTranslation().isEmpty())
            return;
        if (db.findRecord(translator) != null)
            db.editRecord(translator);
        else
            db.addRecord(translator);
    }

    /**
     * Загрузка элемента из истории переводов
     *
     * @param loadedTranslator - загруженный транслятор
     */
    public void loadHistoryItem(Translator loadedTranslator) {
        translator.setTranslator(loadedTranslator);
        etText.setText(translator.getText());
        etText.clearFocus();
    }

    /**
     * Загрузка элемента из избранного
     *
     * @param loadedTranslator - загруженный транслятор
     */
    public void loadFavoriteItem(Translator loadedTranslator) {
        translator.setTranslator(loadedTranslator);
        etText.setText(translator.getText());
        etText.clearFocus();

        ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
    }

    /**
     * Установка флага Избранное текущему слову
     */
    public void setFavorites() {
        if (translator.getIsFavorites())
            translator.setIsFavorites(false);
        else
            translator.setIsFavorites(true);
        Fragment fragment = getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null)
            return;
        TranslatorData translatorData = new TranslatorData();
        Display translationDisplay = new TranslationDisplay(fragment, translatorData);
        translatorData.setTranslator(translator);
        translationDisplay.display();
        saveOrEditRecord(translator);
    }

    /**
     * Вызывается при смене страниц ViewPager
     *
     * @param isVisibleToUser - флаг, показывающий, виден ли фрагмент или нет
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if (isVisibleToUser) {
                if (translatorContext != null)
                    translatorContext.show();
                if(tracker != null){
                    tracker.setScreenName(getClass().getSimpleName());
                    tracker.send(new HitBuilders.ScreenViewBuilder().build());
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранение состояния фрагмента
     * Сохранение текста в поле ввода при сменен состояния Activity
     *
     * @param outState - Bunlde для сохранения состояния
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!etText.getText().toString().isEmpty()) {
            outState.putString(Consts.EXTRA_TEXT, etText.getText().toString());
        }
    }

    /**
     * Вызывается при переходе с Activity для выбора языка обратно в MainActivity
     *
     * @param requestCode - код ответа
     * @param resultCode  - код, показыващий успешно ли выполнена операция
     * @param data        - данные с ответом
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_LANGUAGE:
                resetTranslator(translator);
                String inputLang = LangSharedPreferences.loadInputLanguage(getContext());
                String outputLang = LangSharedPreferences.loadOutputLanguage(getContext());
                translator.setInputLanguage(inputLang);
                translator.setOutputLanguage(outputLang);
                translator.setText(etText.getText().toString());
                translatorContext.show();
                break;
        }
    }

    /**
     * Регистрируем появление фрагмента
     */
    @Override
    public void onResume() {

        super.onResume();

        this.tracker.set(Consts.FIREBASE_ITEM_NAME, getClass().getSimpleName());
        this.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Удаляем View фрагмента
     * Удаляем память под созданные View
     */
    public void onDestroyView() {
        super.onDestroyView();
        etText = null;
    }
}
